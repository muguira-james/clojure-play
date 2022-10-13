(ns luxplore.routes
  (:require [clojure.data.json :as json]
            [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.pprint :as pp]
            [luxplore.indexlucene :as iluc]
            ))

(comment
ideas
done - simple queries on the reader such as number of docs in an index
create a new index
delete an existing inedx
add docs to an index
delete docs from an index

search an index with a simple query
search an index by similarity

)

;; helper function to extract and return json to the caller
(defn return-json-from-body [ request ]
  (let [ body (slurp (:body request))
        js (json/read-str body)]
    js))

(defn map2json [m]
  (json/write-str m))

;;
;; echo a bit of json back to the client
;;
(defn numberOfIndexedDocs
  [request]
  (let* [directory (iluc/openIndex "/home/magoo/code/INDEXDIRECTORY")
         reader (iluc/openReader directory)
         mp {}
         rep (map2json (assoc mp :numdocs (.numDocs reader)))]
    (do
      (iluc/closeIndex)
      (iluc/closeReader)
      {
       :status 200
       :headers {"Content-Type" "application/json"}
       :body rep
       })))

(defn createIndex
  [request]
  (println (str "rt/req -> " (return-json-from-body request)))
  (map2json (assoc {} :indexpath "new indexpath")))


;;
;; echo json back on a post request type
;;
(defn echoPostTest
  [request]
  (let [tst "{ \"path\" : \"./index\" }"]
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body  tst}))

;;
;; my routing table
;;
(defroutes app-routes
  
  (GET "/numdocs" request (numberOfIndexedDocs request))
  (POST "/ptest" request (echoPostTest request))
  (POST "/createindex" request (createIndex request))
  )

