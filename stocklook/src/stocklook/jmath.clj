(ns stocklook.jmath)
;;
(require '[incanter.stats :as istat])
;;
(defn moving-average [lst window-size]
  (loop [ sm []
         lsti (seq lst)
         win-size window-size
         ]
    (if (empty? lsti)
      sm
      (recur (conj sm (istat/mean (first (partition win-size 1 lsti)))) (rest lsti) win-size))))
