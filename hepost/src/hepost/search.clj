
;;
;; from Winston Artificial Intelligence
;;
;; a network search demo
;;
(ns hepost.search)
                                        ;
;; define my network
; (def net
;  {
;   's { :l 'a :r 'd }
;   'a { :l 'b :r 'd }
;   'd { :l 'a :r 'e }
;   'b { :l 'c :r 'e }
;   'c { }
;   'e { :l 'b :r 'f }
;   'f { :l 'z }
;   'z { }
;   })

(def net
  {
   's { :l 'a :r 'g }
   'a { :l 'b :r 'd }
   'b { :l 'c }
   'c { :l 'z }
   'd { :l 'e :r 'f }
   'e { }
   'f { }
   'g { :l 'h :r 'i }
   'h { }
   'i { }
   'z { }
   })

;;
;; use java data structures - these ARE NOT clojure structs and you have to be careful
(def que (java.util.LinkedList.))
(def visit (java.util.LinkedList.))

;;
;; place the item on the front of the list
(defn enque [n]
  (.addFirst que n))

;;
;; this add the item to the  tail of the queue
(defn enque-b [n]
  (.add que n))
;;
;; pop 1 item off the list
(defn decque  []
  (.remove que))

;;
;; clear the que linkedlist
(defn drain-q []
  (.clear que))

;;
;; clear the visit Linkedlist
(defn drain-v []
  (.clear visit))

;; clear them both 
(defn drain-qs []
  (do
    (drain-q)
    (drain-v)))
;;
;; remember where i've been; add nodes to visit
(defn been-here [n]
  (do
    ;; (prn (str "visited= " n))
    (if (not (.contains visit n))
      (.add visit n)
      )))

;;
;; remember the children - adding nodes to the front of the queue
(defn save-children [n]
  (let [left ((net n) :l)
        right ((net n) :r)]
    (do
      (if (and (not (.contains que left)) (not (= left nil)))
        ;; save items on the front of the list
        (enque left))
      (if (and (not (.contains que right)) (not (= right nil)))
        ;; save items on the front of the list
        (enque right)))))

;;
;; generate a que that forces breath first search
(defn save-b-children [n]
  (let [left ((net n) :l)
        right ((net n) :r)]
    (do
      (if (and (not (.contains que left)) (not (= left nil)))
        ;; save items on the end of the list
        (enque-b left))
      (if (and (not (.contains que right)) (not (= right nil)))
        ;; save items on the end of the list
        (enque-b right))
      )))
;;
;; depth first search
;;
;; usage: (d-first que save-children)
(defn d-first [q save-fn]
  (let [node (first q)]
    (if (= node 'z)
      ;; true found our goal
      (do
        (println (str "found my goal -> " node " visit m = " visit))
        'z)
      ;; false: continue searching
      (do
        
        (println (str "moving forward " node " -> " que))
        (decque)
        (been-here node)
        (save-fn node)
        ;; (println (str "checking-> " que))
        (recur  que save-fn)))))

;;
;; entry point for my container
;; you can build a queue with (enque 's)
;; usage:  (search que)
;;
(defn search [q]
  (let [node (first q)]
    (if (= node 'z)
      ;; true found our goal
      (do
        (println (str "found my goal -> " node " visit m = " visit))
        {
         :status 200
         :headers {"Content-Type" "text/html; charset=utf-8"}
         :body (str "<h2> "  visit "</h2>")
         })
      ;; false: continue searching
      (do
        (println (str "moving forward " node " -> " que))
        (decque)
        (been-here node)
        (save-children node)
        ;; (println (str "checking-> " que))
        (recur  que)))))
