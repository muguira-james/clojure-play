(ns clodb.core  
  (:gen-class))

(require '[clojure.java.jdbc :as jdbc])
(require '[feedparser-clj.core :as rss])
(require '[clojure.edn :as edn])

;;
;; -- sample db code

;; (jdbc/query db-map ["SELECT * FROM rss"])
;; (jdbc/insert! db-map :rss {:source s :title t :link link})

;;
;; --- DB code

;; use seeking ;

;; create table rss (source varchar(255), title varchar(255), link varchar(255)) ;

;; select *  from rss ;

(def db-map {
             :dbtype "mysql"
             :dbname "seeking?useSSL=false"
             :user "magoo"
             :password "ipoet"
             })

(defn rss-ddl []
  (jdbc/create-table-ddl
   :rss [[:source "varchar(255)"]
         [:publish_date "varchar(255)"]
         [:title "varchar(255)"]
         [:link "varchar(255)"]
         [:relatedLinks "varchar(255)"]
         ]
   ))

;;
;; insert source title link into db
(defn db-insert!
  [s m]
  (do
    (println (str "--title-->" s "  -> " (answer-title m)))
    (jdbc/insert! db-map :rss {:source s :publish_date (answer-date m) :title (answer-title m) :link (answer-link m)})
    )
  )
;;

;;(defn fill-table []
;;  (jdbc/insert! db-map :rss {:source "seeking alpha" :section "market news" :title "Apple stock crash" :link "http://junk.com"})
;;  )

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



;;
;; loop through a map, insert the "source: title link" into db
(defn pall-title-db [s m]
  (if (not (empty? m))
    (do
      (db-insert! s m)
      (recur s (rest m))))
  ) 



;;
;; loop over the feeds  map and insert all news into db
(defn db-all [coll]
  (if (not (empty? coll))
    (let
        [source (get (first coll) :source)
         link (pop-link (first coll))
         entries (get (rss/parse-feed link) :entries)]
      (do
        (pall-title-db source entries)
        (recur (rest coll))
        )
      )
    )
  )  

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (init-feeds "resources/data.edn")
  (print-all @feeds))

(defn answer-title [m]
  (do
    ;; (println (str "-->>>" m ">>>-->>>" (get m :title)))
    (get m :title)
    )
  )

(defn answer-link [m]
  (get m :link)
  )

(defn answer-date [m]
  (get m :published-date)
  )

(defn all-titles [col]
  (if (not (empty? col))
    (do
      (println (str "-->" (answer-title (first col))))
      
      (recur (rest col))
      )
    )
  )

(defn all-links [col]
  (if (not (empty? col))
    (do
      (println  (str "-->" (answer-link (first col))))
      (recur (rest col))
      )
    )
  )

(defn answer-stock-name [m]
  (get m :name)
  )

(defn answer-stock-ticker [m]
  (get m :taxonomyURI)
  )

(defn all-stocks [meta-map]
  (if (not (empty? meta-map))
    
    (do
      (println (str "-->>-->>" (answer-stock-name (first meta-map)) "-> " (answer-stock-ticker (first meta-map))))
      (recur (rest meta-map))
      
      )
    )
  )

(defn meta-info [meta-col]
  (do
    ;; (println (str "-->>" meta-col))
    (println (str ">>-->>" (answer-title meta-col)))
    (println (str ">>-->>" (answer-link meta-col)))
    (println (str ">>-->>" (answer-date meta-col)))
    (db-insert! (get (first @feeds) :source) meta-col)
    ;; (println)
    )
  )

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

(defn start [col]
  (if (not (empty? col))
    (let
        [entries (get (rss/parse-feed (get (first col) :link)) :entries)]
      
      (walk-meta entries)
      (recur (rest col))
      )
    )
  )



