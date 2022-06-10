(ns http.core
  
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [ring.middleware.cors :refer  [wrap-cors]]	
            [clojure.pprint :as pp]
            [clojure.string :as str]
            [clojure.data.json :as json]
            [http.trawler :as trawl]
            )

  (:gen-class)
  )





(defn scrape-web-page [req]
  (do
    (pp/pprint req)
    (let [p (trawl/fetch (:page (:params req)))
          l (:links p)
          ]
      {
       :status 200
       :headers {"Content-Type" "application/json" }
       :body (->
              (pp/pprint req)
                                        ; (pp/pprint (type p))
              (str " { \"links\" : " (str (json/write-str l)) " } ")
          )
       }
      )))

(defn hello-name [req]
  {:status 200
   :headers { "Content-type" "text/html"}
   :body "Hello"
   })

;; request-example - show the request on the console
(defn request-example [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    (->>
             (pp/pprint req)
             (str "Request Object: " req))})

(defroutes app-routes
  (GET "/request" [] request-example)
  (GET "/" [] hello-name)
  (GET "/scrape" [] scrape-web-page)
  (route/not-found "Error, page not found!"))


(defn -main
  "This is our main entry point"
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    ;; Run the server with Ring.defaults middleware
    (server/run-server (wrap-defaults #'app-routes site-defaults) {:port port})
    ;; Run the server without ring defaults
    ;;(server/run-server #'app-routes {:port port})
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))
