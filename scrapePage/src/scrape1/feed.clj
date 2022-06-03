
(ns scrape1.feed)

(require '[clojure.edn :as edn])
(require '[feedparser-clj.core :as rss])

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
  (spit
   "resources/data.edn"
   (pr-str
    [
     {:source "cnn"
      :link "http://rss.cnn.com/rss/money_news_international.rss"}])))
;;
;; read back that edn from file and print 2 things
(defn read-edn
  [fileName]
  (let [z (edn/read-string (slurp fileName))]
    (println (first z))
    (println (get (first z) :link))
    (get (first z) :link)))

(defn print-feeds []
  (doseq [i @feeds]
    (println i)))

;; ================================================
;;
;; example code to learn about maps and rss/feed reading
;;
;; cnn is a test map to learn with

(def cnn '({:source "hello", :link "bye"} {:source "hello2", :link "bye2"}))


;; first get the rss feed in a lazy-var.
(def rss (rss/parse-feed
          (str "http://rss.cnn.com/rss/money_news_international.rss")))
;;
;; move over to point to just the entries in the RSS feed
(def money (get rss :entries))
                 
  
