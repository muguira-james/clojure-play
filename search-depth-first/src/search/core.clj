(ns search.core
  (:gen-class))

;;
;; define my network
(def net
  {
   's { :l 'a :r 'd }
   'a { :l 'b :r 'd }
   'd { :l 'a :r 'e }
   'b { :l 'c :r 'e }
   'c { }
   'e { :l 'b :r 'f }
   'f { :l 'g }
   'g { }
   })




(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))


(def que (java.util.LinkedList.))
(def visit (java.util.LinkedList.))

;;
;; place the item on the front of the list
(defn enque [n]
  (.addFirst que n))

(defn enque-b [n]
  (.add que n))
;;
;; pop 1 item off the list
(defn decque  []
  (.remove que))

;;
;; clear the list
(defn drain-q []
  (.clear que))

;;
;; clear the list
(defn drain-v []
  (.clear visit))

(defn drain-qs []
  (do
    (drain-q)
    (drain-v)))
;;
;; remember where i've been
(defn been-here [n]
  (do
    ;; (prn (str "visited= " n))
    (if (not (.contains visit n))
      (.add visit n)
      )))

;;
;; remember the children
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
    (if (= node 'g)
      ;; true found our goal
      (do
        (println (str "found my goal -> " node " visit m = " visit))
        'g)
      ;; false: continue searching
      (do
        
        (println (str "moving forward " node " -> " que))
        (decque)
        (been-here node)
        (save-fn node)
        ;; (println (str "checking-> " que))
        (recur  que save-fn)))))



