;;
;; Trawl RSS feeds
;;
;; Using edn based feeds, trawl each feed and return some content.
;;
(ns scrape1.core
  (:gen-class))

(require '[opennlp-clj.core :as nlp])
(require '[scrape1.webtools :as wt])
(require '[clojure.edn :as edn])
(require '[scrape1.buildnewslist :as bnl])
(require '[scrape1.feed :as feed])

;;
;; get all text on the page - this needs to improve to NOT get stuff in menus!!
(defn get-page-text [map-item]
  (get (wt/fetch-web-page-data (bnl/get-link (edn/read-string map-item))) :page-text))

;; get the names of the people mentioned in the page
(defn get-names-on-page [page]
  (nlp/person-names (nlp/tokenize->java page)))

;; get locations mentioned in the page
(defn get-locations-on-page [page]
  (nlp/location-names (nlp/tokenize->java page)))

;;get company names mentioned in the page
(defn get-company-names-on-page [page]
  (nlp/company-names (nlp/tokenize->java page)))

;;---------------------------------------------------------
;;
;; the main just "runs" the app
;;
;; init-feeds creates an atom called feeds.
;; print-all-news: input is the de-ref'ed feeds atom
;;
(defn -main []
  (feed/init-feeds "resources/data.edn")
  (feed/print-feeds)
  (bnl/get-news-list-from-file "temp.str")
  ;; these nest calls should save the text and then operate
  (let [txt (get-page-text (first bnl/news-list))]
    (do
      (println (get-company-names-on-page txt))
      (println (get-names-on-page txt))
      (println (get-locations-on-page txt))))
  ;; finally, count the articles
  (println (str "number of news articles = " (bnl/count-news-list))))
    

    

