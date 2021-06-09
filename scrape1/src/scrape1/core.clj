;;
;; scrape various sites for information
;;
(ns scrape1.core
  (:require [net.cgrand.enlive-html :as html]
            [org.httpkit.client :as http]
            [clojure.edn :as edn]))

(comment
(def feeds [
            { :name "cnn" :link "https://cnn.com/BUSINESS" }
            { :name "bbc" :link "https://www.bbc.com/news/business" }
            { :name "cnbc" :link "https://www.cnbc.com/quotes/V?tab=news" }
            ])
)

(def feeds (atom []))

;;
;; pull in the feeds from the file: fileName
;;
;; same usage: (init-feeds "resources/data.edn")
(defn init-feeds [fileName]
  (reset! feeds
          (edn/read-string (slurp fileName))))

;;
;; the basic test case is cnn business
(def ^:dynamic *base-url* "https://cnn.com/BUSINESS")

;;
;; options to set user-agent and ignore SSL
(def options {:user-agent "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:88.0) Gecko/20100101 Firefox/88.0" :insecure true })

;;
;; gather a dom from cnn-business
(defn get-dom []
  (html/html-snippet
   (:body @(http/get *base-url* options  ))))


;;
;; get a dom from input url
(defn get-dom-from-url [url]
  (html/html-snippet
   (:body @(http/get url options ))))


;;
;; grab from cnn business
(defn extract-titles-cnn [dom]
  (doseq [i (html/select dom [:span.cd__headline-text])]
    (println (first (get i :content)))))

;;
;; grab from bbc business
(defn extract-titles-bbc-business [dom]
  (doseq [i (html/select dom [:h3.gs-c-promo-heading__title])]
    (println (first (get i :content)))))

;;
;; grab from cnbc - this has  the stock ticker built=-in !!!
;;
;; ;; https://www.cnbc.com/quotes/V?tab=news
(defn extract-titles-cnbc [dom]
  (doseq [i (html/select dom [:a.LatestNews-headline])]
    (println (first (get i :content)))))


;; (html/select (html/html-snippet (:body @(http/get "https://www.bbc.com/news/business" {:insecure true}))) [:h3.gs-c-promo-heading__title])
;; Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:88.0) Gecko/20100101 Firefox/88.0
(comment
(defn -main
  "fetch html title from cnn business"
  [& args]
  (do
    (println "----- cnn business-------")
    (extract-titles-cnn (get-dom-from-url "https://cnn.com/BUSINESS"))
    (println "----- bbc  business -------")
    (extract-titles-bbc-business (get-dom-from-url "https://www.bbc.com/news/business"))
    (println "----cnbc VISA --------")
    (extract-titles-cnbc (get-dom-from-url "https://www.cnbc.com/quotes/V?tab=news"))
    ))
 ) 

(defn print-a-feed [source link]
  (do
    (println (str "---------- " source " --------------"))
    (cond (= "cnn" source) (extract-titles-cnn (get-dom-from-url link))
          (= "bbc" source) (extract-titles-bbc-business (get-dom-from-url link))
          (= "cnbc" source) (extract-titles-cnbc (get-dom-from-url link)))
    ))

(defn print-feeds [col]
  (if (not (empty? col))
    (let [source (get (first col) :name)
          link (get (first col) :link)]
      (print-a-feed source link)
      (recur (rest col)))))
    
(defn -main [& args]
  (do
    (init-feeds "resources/feeds.edn")
    (print-feeds @feeds)))
