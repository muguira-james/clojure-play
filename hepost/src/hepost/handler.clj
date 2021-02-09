(ns hepost.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.data.json :as json]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [clojure.pprint :as pprint]
            [hepost.search :as srch]
            ))



(defn return-json-from-body [ request ]
  (let [ body (slurp (:body request))
        js (json/read-str body)]
    js))

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

(def app
  (wrap-defaults app-routes api-defaults))
