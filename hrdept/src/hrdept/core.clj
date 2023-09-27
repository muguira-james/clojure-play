;; example graph create and a couple of simple queries

;; additional notes:
;; This example only uses the default neo4j db!

(ns hrdept.core
  (:require [neo4j-clj.core :as db])
  (:import (java.net URI))
  (:gen-class))

(def local-db
  (db/connect (URI. "bolt://localhost:7687")
              "neo4j"
              "Neo4j851"))

(db/defquery create-cost-center
  "CREATE (c:costcenter {CODE: $cc})")

(db/defquery create-employee
  "CREATE (e:employee {name: $firstName, surname: $SurName})")


(db/defquery employee-reports-to
  "MATCH (e:employee {name: $ename, surname: $sname})
   MATCH (m:employee {name: $m1name, surname: $s1name})
   MERGE (e)-[:REPORTS_TO]->(m) return e, m")


;; (manages-empl "Taylor" "Davies")
(db/defquery employee-manages
  "MATCH (e:employee {name: $ename, surname: $sname})
   MATCH (c:costcenter {CODE: $cc})
   MERGE (e)-[:MANAGER_OF]->(c) return e, c")


(db/defquery employee-belongs-to
  "MATCH (e:employee {name: $ename, surname: $sname})
   MATCH (c:costcenter {CODE: $cc})
   MERGE (e)-[r:BELONGS_TO]->(c) return e, c, r")

(db/defquery delete-all
  "MATCH (d) detach delete d")

(defn delete-detach []
  (db/with-transaction local-db tx
    (println (delete-all tx))))

(db/defquery show-all
  "match (n) return n")
          
(defn match-all []
  (db/with-transaction local-db tx
    (println (show-all tx))))


(db/defquery match-where
  "MATCH (n:employee)--(c:costcenter)
   where c.CODE = 'CC1' return n, c")

(defn show-match []
  (db/with-transaction local-db tx
    (prn (match-where tx))))



(db/defquery m-m-r
  "MATCH (n)-[:BELONGS_TO]->(c:costcenter)<-[:MANAGER_OF]-(m)
   return n.surname, m.surname, c.CODE")

(defn show-mmr[]
  (db/with-transaction local-db tx
    (prn (m-m-r tx))))

(db/defquery m-r
  "MATCH (n:employee)-[r:BELONGS_TO|MANAGER_OF]->(c:costcenter)
   where c.CODE = 'CC1'
   return n.name, r")
  
(defn show-mr []
  (db/with-transaction  local-db tx
    (println (m-r tx))))
      

(defn create-init-graph []
  (with-open [session (db/get-session local-db)]
    (do
       (create-cost-center session {:cc "CC1"})
       (create-cost-center session {:cc "CC2"})

       (create-employee session {:firstName "Nathan" :SurName "Davies"})
       (create-employee session {:firstName "Rose" :SurName "Taylor"})
       (create-employee session {:firstName "John" :SurName "Smith"})
       (create-employee session {:firstName "Heather" :SurName "Underwood"})
      

       (employee-belongs-to session {:ename "Nathan" :sname "Davies" :cc "CC1"})
       (employee-reports-to session {:ename "Nathan" :sname "Davies" :m1name "Rose" :s1name "Taylor"})

       (employee-manages session {:ename "Rose" :sname "Taylor" :cc "CC1"})

       (employee-belongs-to session {:ename "John" :sname "Smith" :cc "CC2"})
       (employee-reports-to session {:ename "John" :sname "Smith" :m1name "Heather" :s1name "Underwood"})

       (employee-belongs-to session {:ename "Heather" :sname "Underwood" :cc "CC2"})
       (println "done")
      )))

(defn -main
  "create CostCenter nodes, Employee nodes"
  [& args]
  (do
    (create-init-graph)
    (println "you can delete the graph with (delete-detach)")))


