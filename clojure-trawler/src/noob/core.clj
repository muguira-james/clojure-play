;;
;; Trawl RSS feeds
;;
;; Using edn based feeds, trawl each feed and return some content.
;;
(ns noob.core
  (:gen-class))


(require '[feedparser-clj.core :as rss])
(require '[clojure.edn :as edn])
(require '[clojure.java.io :as io])

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

;; ================================================
;;
;; example code to learn about maps and rss/feed reading
;;
;; cnn is a test map to learn with

(def cnn '({:source "hello", :link "bye"} {:source "hello2", :link "bye2"}))


;; first get the rss feed in a lazy-var.
(def rss (rss/parse-feed (str "http://rss.cnn.com/rss/money_news_international.rss")))
;;
;; move over to point to just the entries in the RSS feed
(def money (get rss :entries))

;; ================================================
;;
;;
;; just return the title
(defn get-title [m]
  (get m :title))
;;

;;
;; return the source 
(defn get-source [item]
  (get item :source))
;;
;; just return the link
(defn get-link [m]
  (get m :link))

(defn get-entries [entry]
  (get (rss/parse-feed (get-link entry)) :entries))


;;
;; the new list as a java ArrayList
;;
(def news-list (new java.util.ArrayList))
;;
;;
;;
;; create a hash of these items
(defn build-news-item [source title link]
  { :source source :title title :link link})


;;
;; create the new list
(defn build-news-list [collection]
  (with-open [w (clojure.java.io/writer "temp.str")]
    (doseq [item collection]
      (let [entries (get-entries item)
            source (get-source item)]
        (doseq [i entries]
          (.add news-list
                (pr-str
                 (build-news-item source (get-title i) (get-link i)))))))))

  

;; (build-news-list @feeds news-list)
(defn print-news-list []
  (let [size (.size news-list)]
    (do
      (println (str "size = " (.size news-list)))
      (doseq [i (range size)]
        (println (str (.get news-list i)))))))


;;
;; build a news list from a collection of feed items
(defn buld-n-print-news-list [collection]
  (doseq [item collection]
    (let [entries (get-entries item)
          source (get-source item)]
      (doseq [i entries]
        (println 
         (str (build-news-item
               source
               (get-title i)
               (get-link i))))))))
                  
      
;;---------------------------------------------------------
;;
;; the main just "runs" the app
;;
;; init-feeds creates an atom called feeds.
;; print-all-news: input is the de-ref'ed feeds atom
;;
(defn -main []
  (do
    (init-feeds "resources/data.edn")
    (build-news-list @feeds)
    (print-news-list)))
    

