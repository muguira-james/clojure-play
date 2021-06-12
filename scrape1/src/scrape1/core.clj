;;
;; scrape various sites for information
;;
;; right the list is
;;  cnn business
;;  bbc business
;;  cnbc
;;
(ns scrape1.core
  (:require 
   [scrape1.urls :as url]
   [scrape1.utils :as utils]
   [scrape1.feedsDB :as fd] ))

        

;; ----------------- output file handling ------------------
;;
;; use cnbc and write the map to a file
(defn loop-thru-sequence [seq fh]
  (if (not (empty? seq))
    (let [it (first seq)
          title (utils/answer-title it)
          link (get (get it :attrs) :href)]
      (.write fh (utils/build-map-record title link))
      (recur (rest seq) fh))))

;;
;; create file from an input seq of maps (from cnbc)
(defn build-file [ fileName seq]
    (with-open [fh (clojure.java.io/writer fileName )]
      (do
        (.write fh (str "[ "))
        (loop-thru-sequence seq fh)
        (.write fh " ]"))))

;; ----------- presentation code ----------------------

(defn print-a-news-item [source link]
  (do
    (println (str "---------- " source " --------------"))
    (cond (= "cnn" source) (utils/print-titles (url/extract-cnn-business-to-seq link))
          (= "bbc" source) (utils/print-titles (url/extract-bbc-business-to-seq link))
          (= "cnbc" source) (utils/print-titles (url/extract-cnbc-to-seq link))
          :else (println (str "--> did not recognize this source: --> " source))
    )))

(defn print-news [col]
  (doseq [[k v] col]
    (let [source k
          link (get v :link)]
      (print-a-news-item source link)
      )))

(defn cnbc-news [ticker]
  (let [base (get (get @fd/feeds "cnbc") :base)
        suf (get (get @fd/feeds "cnbc") :tab)]
    (utils/print-titles (url/extract-cnbc base ticker suf))))


(defn -main [& args]
  (do
    (fd/init-feeds "resources/feed2.edn")
    (print-news @fd/feeds)
    (build-file "out2.txt" (url/extract-cnbc-to-seq "https://www.cnbc.com/quotes/V?tab=news"))))

