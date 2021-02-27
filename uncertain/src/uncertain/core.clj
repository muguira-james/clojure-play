(ns uncertain.core
  (:require [neo4j-clj.core :as db])
  (:import  (java.net URI))
  (:gen-class))


(comment "


1	LONGi Solar	China
2	JinkoSolar	China
3	JA Solar	China
4	Trina Solar	China
5	Canadian Solar	Canada
6	Hanwha Q-CELLS	South Korea
7	Risen Energy	China
8	Astroenergy	China
9	First Solar	USA
10	Suntech	China

How rate them

Name Plate power rating
power tolerance
Solar cell effeciency -just under 23%
Termperater coefficiency
Snow and wind load


JinkoSolar Holding Co. Ltd. JKS (China)

    Revenue TTM: $4.6 billion
    Net Income TTM: $163.1 million
    Market Cap: $909.9 million
    1-Year Trailing Total Return: 6.9%
    Exchange: New York Stock Exchange

Canadian Solar Inc. (CSIQ)

    Revenue (TTM): $3.2 billion
    Net Income (TTM): $257.3 million
    Market Cap: $1.6 billion
    1-Year Trailing Total Return: 30.5%
    Exchange: NASDAQ

"
         )

(def local-db
  (db/connect (URI. "bolt://localhost:7687")
              "neo4j"
              "t1y2p3e4"))

(db/defquery create-user
  "CREATE (u:user $user)")

(db/defquery get-all-users
  "match (u:user) return u as user")

(defn -main
  "add a  single user to a single db instance"
  [& args]
  (with-open [session (db/get-session local-db)]
    (create-user session {:user {:first-name "Julie" :last-name "Ray"}})
    (create-user session {:user {:first-name "Mark" :last-name "Ray"}})
    (create-user session {:user {:first-name "Paul" :last-name "Brown"}})
    (println (get-all-users session))
    )) 
