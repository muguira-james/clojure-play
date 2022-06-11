(ns nlu0.core

  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [ring.middleware.cors :refer  [wrap-cors]]	
            [clojure.pprint :as pp]
            [clojure.string :as str]
            [clojure.data.json :as json]
            [opennlp-clj.core :as nlp])
 
  (:gen-class))



(def sent "The world post was in shock today because Steve Jobs has returned. He was seen in Richmond Va.")

;;
;; helper function to extract and return json to the caller
(defn return-json-from-body [ request ]
  (let [ body (slurp (:body request))
        js (json/read-str body)]
    js))


;;
;; handle processing the json and calling search with the starting point
(defn build-post-response [request]
  (let [post-data (return-json-from-body request)]
    (do
      (pp/pprint post-data)
      {:status 200
       :headers {"Content-Type" "application/json"}
       :body "{ \"foo\" : 1 }"
       })
  ))

;;
;; a test function
(defn hello-request [req]
  {:status 200
   :headers { "Content-type" "text/html"}
   :body    (->>
             (pp/pprint req)
             (with-out-str (pp/pprint req)))
   })

(defn do-post [req]
  (do
    ;; get request body, turn it into a map
    (let [it (return-json-from-body req)]
      (pp/pprint (str "json -> " it))
      ;; return that map
      {:status  200
       :headers {"Content-Type" "text/html"}
       :body it})))

(defn do-nlp [req]
  (let [people (nlp/person-names (nlp/tokenize->java sent))
        locations (nlp/location-names (nlp/tokenize->java sent))
        mm (hash-map :people people :locations locations)]
    
    {:status  200
       :headers {"Content-Type" "text/html"}
       :body (json/write-str mm)}))

(defroutes app-routes
  (GET "/" [] hello-request)
  (GET "/nlp" [] do-nlp)
  (POST "/agent" request (do-post request))
  (route/not-found "opps, page not found!"))

(def app
  (->
   app-routes
   (wrap-defaults api-defaults)
   (wrap-cors
    :access-control-allow-origin [#".*"]
    :access-control-allow-methods [:get :post])

   ))
;;
;; to run:
;; $ PORT=8000 lein run
;;
;; default port = 3000
;;
(defn -main
  "This is our main entry point"
  [& args]
  ;; 1st reach into the env to get the port, or use the default
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    ;; Run the server with Ring.defaults middleware
    (server/run-server app {:port port})
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))

