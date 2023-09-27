;; show how to use cypher to connect to a different DB (not the default)

(ns hrdept.core
  (:require [neo4j-clj.core :as db])
  (:import (java.net URI))
  (:gen-class))

(def local-db
  (db/connect (URI. "bolt://localhost:7687")
              "neo4j"
              "Neo4j851")) ; notice the capital letter!!



(db/defquery create-cost-center
  "USE sampledb CREATE (c:costcenter {CODE: $cc})")


(defn in-new-db []
  (with-open [session (db/get-session local-db)]
    (create-cost-center session {:cc "CC3"})))

(db/defquery create-empl
  "USE sampledb CREATE (e:employee {name: $name})")

(defn do-1-empl []
  (with-open [session (db/get-session local-db)]
    (create-empl session {:name "James"})))

(db/defquery employee-belongs-to
  "USE sampledb MATCH (e:employee {name: $ename}),
                MATCH (c:costcenter {CODE: $cc})
                MERGE (e)-[:BELONGS_TO]->(c) return e, c")
                                        ; "USE sampleDB MERGE (e:employee {name: $ename})-[r:BELONGS_TO]->(c:costcenter {CODE: $cc}) ")
  

(defn connect-em []
  (with-open [session (db/get-session local-db)]
    (employee-belongs-to session {:ename "James" :cc "CC3"})))
