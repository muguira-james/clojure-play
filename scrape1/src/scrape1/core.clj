;;
;; scrape various sites for information
;;
;; right the list is
;;  cnn business
;;  bbc business
;;  cnbc
;;
(ns scrape1.core
  (:require [net.cgrand.enlive-html :as html]
            [org.httpkit.client :as http]
            [clojure.edn :as edn]))

;; --------------- handle the input DB --------------------
(comment

  this is an example of what the feeds file could look like
  name is the slector for the feed low-level processor (i.e. css selector)
  link is the pointer (url) to the page

  OLD FEEDS format !!!
(def feeds [
            { :name "cnn" :link "https://cnn.com/BUSINESS" }
            { :name "bbc" :link "https://www.bbc.com/news/business" }
            { :name "cnbc" :link "https://www.cnbc.com/quotes/V?tab=news" :base "https://www.cnbc.com/quotes" :tab "?tab=news" }
            ])

NEW FEED FORMAT !

(def feeds { "cnn" { :name "CNN Business" :link "https://cnn.com/BUSINESS" }
             "bbc" { :name "BBC business" :link  "https://www.bbc.com/news/business" }
            "cnbc" { :name "CNBC"         :link "https://www.cnbc.com/quotes/V?tab=news" :base "https://www.cnbc.com/quotes" :tab "?tab=news" }
            }
)

(def feeds (atom {}))

;;
;; pull in the feeds from the file: fileName
;;
;; same usage: (init-feeds "resources/data.edn")
(defn init-feeds [fileName]
  (reset! feeds
          (edn/read-string (slurp fileName))))

;;------------------- handle URL --------------------------
;;
;; the basic test case is cnn business
(def ^:dynamic *base-url* "https://cnn.com/BUSINESS")

;;

;;
;; options to set user-agent and ignore SSL
(def options {:user-agent "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:88.0) Gecko/20100101 Firefox/88.0" :insecure true })

;;
;; gather a dom from cnn-business
(defn get-dom []
  (html/html-snippet
   (:body @(http/get *base-url* options  ))))


;;
;; get a dom from input url
(defn get-dom-from-url [url]
  (html/html-snippet
   (:body @(http/get url options ))))

;; ----------------- extract to sequence -------------------

(defn extract-cnbc-to-seq [url]
  (html/select (get-dom-from-url url) [:a.LatestNews-headline]))

(defn extract-cnn-business-to-seq [url]
  (html/select (get-dom-from-url url) [:span.cd__headline-text]))

(defn extract-bbc-business-to-seq [url]
  (html/select (get-dom-from-url url) [:h3.gs-c-promo-heading__title]))

(defn extract-cnbc [ base ticker tab ]
  (let [url (str base ticker tab)]
    (extract-cnbc-to-seq url)))
        
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


;; ----------------- output file handling ------------------
;;
;; use cnbc and write the map to a file
(defn loop-thru-sequence [seq fh]
  (if (not (empty? seq))
    (let [it (first seq)
          title (answer-title it)
          link (get (get it :attrs) :href)]
      (.write fh (build-map-record title link))
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
    (cond (= "cnn" source) (print-titles (extract-cnn-business-to-seq link))
          (= "bbc" source) (print-titles (extract-bbc-business-to-seq link))
          (= "cnbc" source) (print-titles (extract-cnbc-to-seq link))
          :else (println (str "--> did not recognize this source: -->" source))
    )))

(defn print-news [col]
  (doseq [[k v] @feeds]
    (let [source k
          link (get v :link)]
      (print-a-news-item source link)
      )))


(defn -main [& args]
  (do
    (init-feeds "resources/feed2.edn")
    (print-news @feeds)
    (build-file "out2.txt" (extract-cnbc-to-seq "https://www.cnbc.com/quotes/V?tab=news"))))

