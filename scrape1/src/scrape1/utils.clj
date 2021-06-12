(ns scrape1.utils)

;; ---------------- print ---------------------------------
;;
;; grab and print the titles from a seq
(defn print-titles [seq]
  (doseq [i seq]
    (println (first (get i :content)))))


;;
;; given a map pull out the title
(defn answer-title [m]
  (first (get m :content)))

;;
;; given a  map pull out the link
(defn answer-link [m]
  (get (get m :attrs) :href))

;;
;; make a pretty map of the title and link
(defn  build-map-record [title link]
  (str "{ :title \"" title "\" :link \"" link "\" }" ))

(comment

  example code to print a seq of cnbc dom
  
(defn p-seq [seq]
  (if (not (empty? seq))
    (let [it (first (get (first seq) :content))]
      (println it)
      (recur (rest seq)))))
)

;;
;; use cnbc dom and neatly print the title and link
(defn print-seq-neat [seq]
  (if (not (empty? seq))
    (let [it (first seq)
          title (answer-title it)
          link (get (get it :attrs) :href)]
      (do
        (println title)
        (println)
        (println link)
        (println)
        (recur (rest seq))))))

;;
;; use cnbc and print the titlle and link in a map
(defn print-seq [seq]
  (if (not (empty? seq))
    (let [it (first seq)
          title (answer-title it)
          link (get (get it :attrs) :href)]
      (do
        (println (build-map-record title link))
        (recur (rest seq))))))

