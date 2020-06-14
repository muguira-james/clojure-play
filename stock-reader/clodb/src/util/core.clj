(ns util.core
  (:gen-class))


;;
;; retrieve the title from the map
(defn answer-title [m]
  (do
    ;; (println (str "-->>>" m ">>>-->>>" (get m :title)))
    (get m :title)
    )
  )

;;
;; return the link from the map
(defn answer-link [m]
  (get m :link)
  )

;;
;; return the publish_date from the map
(defn answer-date [m]
  (get m :published-date)
  )

;;
;; helper function to walk the collection and print all titles
(defn all-titles [col]
  (if (not (empty? col))
    (do
      (println (str "-->" (answer-title (first col))))
      
      (recur (rest col))
      )
    )
  )

;;
;; helper function to walk the collection and print all the links
(defn all-links [col]
  (if (not (empty? col))
    (do
      (println  (str "-->" (answer-link (first col))))
      (recur (rest col))
      )
    )
  )

;;
;; return stock name from the map
(defn answer-stock-name [m]
  (get m :name)
  )

;;
;; return the stock ticker from the map
(defn answer-stock-ticker [m]
  (get m :taxonomyURI)
  )
