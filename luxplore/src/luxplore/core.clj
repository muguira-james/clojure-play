(ns luxplore.core
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [ring.middleware.cors :refer  [wrap-cors]]	
            [clojure.pprint :as pp]
            [clojure.string :as str]
            [clojure.data.json :as json]
            [luxplore.routes :as rt]
            [luxplore.indexlucene :as iluc]
            )
  (:gen-class))

;;
;; should only run a single instance of the server at any point
;; in time.  This makes sure that is ALWAYS TRUE
;;
(defonce app-server (atom nil))

;;
;; add all the middleware
;; e.g. retrieve the body from a post with wrap-defaults
;;
(def app
  (->
   rt/app-routes
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
  "server entry point"
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    ;; Run the server with Ring.defaults middleware
    (reset! app-server (server/run-server app {:port port}))
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))

;;
;; stop the server in the repl
;;
(defn stop-server
  []
  (when-not (nil? @app-server)
    (@app-server :timeout 100)
    (reset! app-server nil)
    (println "INFO: Application server stopped")))

;;
;; start the server in the repl
;;
;; make sure there is only 1 instance
;;
(defn restart-server
  []
  (stop-server)
  (-main))


