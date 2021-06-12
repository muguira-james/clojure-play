(ns scrape1.feedsDB)

(require '[clojure.edn :as edn] )


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
            })
)

(def feeds (atom {}))

;;
;; pull in the feeds from the file: fileName
;;
;; same usage: (init-feeds "resources/data.edn")
(defn init-feeds [fileName]
  (reset! feeds
          (edn/read-string (slurp fileName))))

