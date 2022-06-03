;;
;; Trawl RSS feeds
;;
;; Using edn based feeds, trawl each feed and return some content.
;;
(ns scrape1.buildnewslist)



(require '[clojure.edn :as edn])
(require '[clojure.java.io :as io])
(require '[feedparser-clj.core :as rss])
;; ================================================
;;
;;
;; just return the title
(defn get-title [m]
  (get m :title))
;;

;;
;; return the source 
(defn get-source [item]
  (get item :source))
;;
;; just return the link
(defn get-link [m]
  (get m :link))

(defn get-entries [entry]
  (get (rss/parse-feed (get-link entry)) :entries))


;;
;; the new list as a java ArrayList
;;
(def news-list (new java.util.ArrayList))
;;
;;
;;
;; create a hash of these items
(defn build-news-item [source title link]
  { :source source :title title :link link})


;;
;; create the new list; return sthe number of items added to the list
(defn build-news-list [collection]
  ;; 1st clear the list!
  (.clear news-list)
  ;; loop over the collection, building tuples of { source, title link }
  (doseq [item collection]
    (let [entries (get-entries item)
          source (get-source item)]
      ;; loop over entries, adding them to the javaArrayList
      (doseq [i entries]
        ;; add each entry to the news-list (a java array)
        (.add news-list
              ;; create a hash map and add to the java array list
              (build-news-item source (get-title i) (get-link i))))))
  (count news-list))

;; read the fileName into the news-list javaArrayList
(defn get-news-list-from-file [fileName]
  (let [entries (edn/read-string (slurp fileName))]
    (.clear news-list)
    (doseq [i entries]
      (.add news-list i)))
  (count news-list))

;; dump the news-list to a fileName
(defn create-news-list-file [fileName]
  (spit "temp.str" (pr-str news-list)))

;; print the news-list
(defn print-news-list []
  (let [size (.size news-list)]
    (do
      (println (str "size = " (.size news-list)))
      (doseq [i (range size)]
        (println (str (.get news-list i)))))))


;;
;; build a news list from a collection of feed items
(defn buld-n-print-news-list [collection]
  (doseq [item collection]
    (let [entries (get-entries item)
          source (get-source item)]
      (doseq [i entries]
        (println 
         (str (build-news-item
               source
               (get-title i)
               (get-link i))))))))
                  
(defn count-news-list []
  (count news-list))

