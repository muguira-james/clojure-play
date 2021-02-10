
;;
;; demo how to use a web server
;;
;; the code computes a path through a network using a hard coded depth-first search.
;; The code can handle both depth and breath first.  I just coded this for depth.
;;
;; a client sends a starting point: the name of the starting point, e.g. "s"
;; the starting point is encoded in json and sent in the body of the POST request
;;
;; note the network is hard coded in the search package
;;
(ns hepost.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.data.json :as json]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [clojure.pprint :as pprint]
            [hepost.search :as srch]
            ))


;;
;; helper function to extract and return json to the caller
(defn return-json-from-body [ request ]
  (let [ body (slurp (:body request))
        js (json/read-str body)]
    js))

;;
;; handle processing the json and calling search with the starting point
(defn build-post-response [request]
  (let [myqitem ((return-json-from-body request) "que")]
    (do
      (srch/drain-q)
      (srch/drain-v)
      (cond
        (= myqitem "s") (srch/enque 's)
        (= myqitem "a") (srch/enque 'a)
        (= myqitem "d") (srch/enque 'd)
        (= myqitem "e") (srch/enque 'e))
      
      (pprint/pprint (str "que-> " srch/que))
      (srch/search srch/que)
      ))
  )

;;
;; define my 3 routes:
;; "/" is a test route
;; "/abba" is learning how to get to the body of the request
;; "/search" does the search call
;;
(defroutes app-routes
  (GET "/" [] {
               :status 200
               :headers {"Content-Type" "text/html; charset=utf-8"}
               :body "Hello James"
               }
       )
  (POST "/abba"   request
        (do
          (pprint/pprint request)
          (println )
          (str "<h1>Hello agent: " ((build-post-response request) "name") "</h1>"))
        )
  (POST "/search" request
        (do
          (build-post-response request)
          )
        )
  (route/not-found "Not Found")
  )

;;
;; get the server started - note: i changed site-defaults to api-defaults
(def app
  (wrap-defaults app-routes api-defaults))
