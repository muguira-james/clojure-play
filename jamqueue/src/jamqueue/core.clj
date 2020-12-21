;;
;; create, list enq & deq - handle message queues
;;
;; create = define a topic to queue messages to (FIFO)
;; eng data to a specific topic
;; deq data from a specific topic, pops the first item from topic
;;
;; notes;
;;   will hang if you don't have something in the topic name
;;
;; improvements:
;;   return a payload structure that indicates error or success
;;
(ns jamqueue.core
  (:require [clojure.core.async :as a
             :refer [>! <! >!! <!! go chan buffer close! thread alts! alts!! timeout]]
            [ring.adapter.jetty :as jetty]
            [clojure.pprint :as pprint]
            [clojure.data.json :as json]
            [ring.middleware.cors  :refer [wrap-cors]]
            [ring.middleware.json :as rjson]
            [ring.util.response :as rr]
            )
  (:gen-class))

; (def echo-chan (chan)) ; create a channel
; (go (println (<! echo-chan)))  ; wait for something to go on the channel

; (>!! echo-chan "ketchup") ; put something on the channel

;;
;; define my single topic, which is a CSP channel

(def topic-channels {})

;;
;; tell me if there is a topic by this name
(defn has-topic [q topic]
  (if (contains? q topic)
    true
    false
    ))
;;
;; add a topic to the topic map
(defn create-topic [topic-name]
  (def topic-channels (assoc topic-channels topic-name (chan))))

;;
;; add payload to channel
(defn  enq [topic payload]
  (go (>!! (topic-channels topic) payload))
  )


;;
;; remove last item from channel 
(defn deq [topic]
  (<!! (topic-channels topic)))
  
    
;;
;; route not found handler
(defn route-not-found  []
    {
     :status 200
     :header {"Content-Type" "text/plain"}
     :body (str "route not found")
     }
  )

;;
;; return the body from a request
(defn get-body [request]
  (json/read-str (slurp (:body request)) :key-fn keyword))

;;
;; return the topic from a body
(defn get-topic [body]
  (:topic body))

;;
;; enq handler - handle an enqueue request
(defn handler-enq [request]
  (let [body (get-body request)]
    (do
      (pprint/pprint (str "body==> " body))
      (if (has-topic topic-channels (body :topic))
        ; if topic is present enq and send a positive response
        (do
          (enq ( :topic body) (:payload body))
          {
           :status 200
           :header {"Content-Type" "text/plain"}
           :body (str "->topic: " (:topic body) " ->payload: " ( :payload body))
           })
        
        ; if the topic is NOT present - send a negative response
        {
         :status 400
         :header {"Content-Type" "text/plain"}
         :body "topic not found"
         })
      )))

;;
;; handle a dequeue request
(defn handler-deq [request]
  (let [ mydata (deq (get-topic (get-body request)))]
    (do
      (println (str mydata))
      {
       :status 200
       :header {"Content-Type" "text/plain"}
       :body (str "<div>" mydata "</div>")
       }
      )))

;;
;; handle a create topic request
(defn handler-create [request]
  (let [body (json/read-str (slurp (:body request)) :key-fn keyword)]
    (do
      (create-topic (body :topic))
      {
       :status 200
       :header {"Content-Type" "text/plain"}
       :body (str "<div>Topic: " (body :topic) " created</div>")
      }
      )))

;;
;; handle a list topcis request
(defn handler-list [request]
  {
   :status 200
   :header {"Content-Type" "text/plain"}
   :body (str (keys topic-channels))
   }
  )

;;
;; routes for the program
(defn routes [request]
  (let [uri (:uri request)]
    (case uri
      "/enq" (handler-enq request)
      "/deq" (handler-deq request)
      "/create" (handler-create request)
      "/list" (handler-list request)
      (route-not-found request)
      )))

(def app
  (do
    (-> routes
        rjson/wrap-json-response ; make  the response a json  object
        (wrap-cors :access-control-allow-origin [#".*"] :access-control-allow-methods [:get :post])
        )))

(defn -main  []
  "start jetty and handle requests"
  (jetty/run-jetty app {:port 5000}))


