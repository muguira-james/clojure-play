(ns feeds.core  
  (:gen-class))

(require '[feedparser-clj.core :as rss])
(require '[clojure.edn :as edn])

;;
;; First setup and use the edn file to get configuration
;;
;; The next 4 forms:
;; feeds is the configuration atom
;; init-feeds pulls config in from disk
;; save-edn is my experimentation with creating a edn
;; read-end is experimentation with reading one from disk


;;
;; set up the extensible data notation read (edn)
(def feeds (atom []))
;;
;; pull in the feeds from the file: fileName
(defn init-feeds [fileName]
  (reset! feeds
          (edn/read-string (slurp fileName))))
;;
;;
;; This is an example of what ann "EDN" file might look like
;; save a copy of the config to a file in resources dir
(defn save-edn
  []
  (spit "resources/data.edn" (pr-str [{:source "cnn" :link "http://rss.cnn.com/rss/money_news_international.rss"}])))
;;
;; read back that edn from file and print 2 things
(defn read-edn
  [fileName]
  (let [z (edn/read-string (slurp fileName))]
    (println (first z))
    (println (get (first z) :link))
    (get (first z) :link)))

