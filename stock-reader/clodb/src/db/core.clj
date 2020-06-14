(ns db.core
  (:gen-class))

(require '[clojure.java.jdbc :as jdbc])
(require '[util.core :as util])
;;
;; -- sample db code

;; (jdbc/query db-map ["SELECT * FROM rss"])
;; (jdbc/insert! db-map :rss {:source s :title t :link link})

;;
;; --- DB code

;; use seeking ;

;; create table rss (source varchar(255), title varchar(255), publish_date varchar(255), link varchar(255)) ;

;; select *  from rss ;

;;(defn fill-table []
;;  (jdbc/insert! db-map :rss {:source "seeking alpha" :section "market news" :title "Apple stock crash" :link "http://junk.com"})
;;  )



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
    (println (str "--title-->" s "  -> " (util/answer-title m)))
    (jdbc/insert! db-map :rss {:source s :publish_date (util/answer-date m) :title (util/answer-title m) :link (util/answer-link m)})
    )
  )
