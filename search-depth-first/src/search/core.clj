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


;;
;; lst is the working list of nodes I've visited
(def lst ())

;;
;; q is the path through the network
(def q [])

;;
;; add item i onto lst
(defn enq [i]
  (def lst (conj lst i)))

;;
;; save item i on to q (i.e. the path through the network
(defn save-visited [i]
  (def q (conj q i)))

;;
;; remove first node from lst
(defn deq []
  (def lst (pop lst)))

;;
;; remove all nodes from lst
(defn drain-lst []
  (while (not-empty lst)
    (def lst (pop lst))))

;;
;; remove all nodes from q
(defn drain-q []
  (while (not-empty q)
    (def q (pop q))))

;;
;; for this node, add children to lst
(defn addchildren [n]
  (let [left ((net n) :l)
        right ((net n) :r)]
    (do
      (println left right)
      (if-not (nil? left)
        (enq left)
        (println "left is nil"))
      (if-not (nil? right)
        (enq right)
        (println "right is nil"))
      )))
        
;;
(defn depth-first []
  "search the net using depth-first"
  (while (not-empty lst)
    (let [node (peek lst)]
      (do
        (println (str "node-->  " node " lst-> " lst))
        (save-visited node)
        ; if current node is our destination: print some info, drain the queue and stop
        (if (= node 'g)
          (do
            (println (str  "found goal: final path-> " q))
            (drain-lst))
          (do
            (deq)
            (addchildren node))))

      )))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
