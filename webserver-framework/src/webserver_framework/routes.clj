(ns webserver-framework.routes
  (:require [clojure.data.json :as json]
            [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.pprint :as pp]
            ))

;; helper function to extract and return json to the caller
(defn return-json-from-body [ request ]
  (let [ body (slurp (:body request))
        js (json/read-str body)]
    js))

;;
;; echo the request back the client
;;
(defn echoRequest [request]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body  (with-out-str (pp/pprint request))})

;;
;; echo a bit of json back to the client
;;
(defn echoTest
  [request]
  (let [tst "{ \"foo\" : \"james\" }"]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body  tst}))

;;
;; echo json back on a post request type
;;
(defn echoPostTest
  [request]
  (let [tst "{ \"james\" : \"muguira\" }"]
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body  tst}))

;;
;; echo the sent json back, logging as you go
;;
(defn echoPOSTBody
  [request]
  (do
    (let [rtnbody (return-json-from-body request)]
      (println (str " body -> " rtnbody))
      {:status 200
       :headers {"Content-Type" "application/json"}
       :body  (with-out-str (pp/pprint rtnbody))}
      )))
;;
;; my routing table
;;
(defroutes app-routes
  (GET "/" request (echoRequest request))
  (GET "/test" request (echoTest request))
  (POST "/ptest" request (echoPostTest request))
  (POST "/pbtest" request (echoPOSTBody request))
  )

