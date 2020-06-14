(ns clodb.core  
  (:gen-class))

(require '[clojure.java.jdbc :as jdbc])
(require '[feedparser-clj.core :as rss])
(require '[feeds.core :as feed])
(require '[util.core :as util])
(require '[db.core :as db])

;;
;; helper function to walk the collection and print all stock names and tickers
(defn all-stocks [meta-map]
  (if (not (empty? meta-map))
    
    (do
      (println (str "-->>-->>" (util/answer-stock-name (first meta-map)) "-> " (util/answer-stock-ticker (first meta-map))))
      (recur (rest meta-map))
      
      )
    )
  )

;;
;; print out the title, link and publish_date, then drop all that in the db
(defn meta-info [meta-col]
  (do
    ;; (println (str "-->>" meta-col))
    (println (str ">>-->>" (util/answer-title meta-col)))
    (println (str ">>-->>" (util/answer-link meta-col)))
    (println (str ">>-->>" (util/answer-date meta-col)))
    (db/db-insert! (get (first @feed/feeds) :source) meta-col)
    ;; (println)
    )
  )
                                        ;
;; print out the title, link and publish_date, then drop all that in the db
(defn meta-info-nodb [meta-col]
  (do
    ;; (println (str "-->>" meta-col))
    (println (str ">>-->>" (util/answer-title meta-col)))
    (println (str ">>-->>" (util/answer-link meta-col)))
    (println (str ">>-->>" (util/answer-date meta-col)))
    ;; (println)
    )
  )

;;
;; walk down the collection print info
(defn walk-meta [collection]
  (if (not (empty? collection))
    (do
      (meta-info (first collection))
      (all-stocks (get (first collection) :categories))
      (println )
      (recur (rest collection))
      )
    )
  )

;;
;; walk down the collection print info, DONT add to db
(defn walk-meta-nodb [collection]
  (if (not (empty? collection))
    (do
      (meta-info (first collection))
      (all-stocks (get (first collection) :categories))
      (println )
      (recur (rest collection))
      )
    )
  )

;;
;; start up the collection walk
(defn start [col]
  (if (not (empty? col))
    (let
        [entries (get (rss/parse-feed (get (first col) :link)) :entries)]
      
      (walk-meta entries)
      (recur (rest col))
      )
    )
  )

;;
;; starup without adding to the db
(defn start-nodb [col]
  (if (not (empty? col))
    (let
        [entries (get (rss/parse-feed (get (first col) :link)) :entries)]
      
      (walk-meta-nodb entries)
      (recur (rest col))
      )
    )
  )


(defn -main
  "gather the initial feeds I'll read and insert them into the db"
  [& args]
  (feed/init-feeds "resources/feed-config.edn")
  (start @feed/feeds)
  )

;;
;; main without db insert
(defn -main-nodb
  "gather the initial feeds I'll read and insert them into the db"
  [& args]
  (feed/init-feeds "resources/feed-config.edn")
  (start @feed/feeds)
  )



