(ns moon.core
  (:require [clj-time.core :refer [date-time now]])
  (:require [clj-time.format])
  (:require [clojure.java.io :refer [reader]])
  (:require [clojure.string :refer [split]])
  (:gen-class))

(def date-fmt (clj-time.format/formatters :date))

(defn read-dates
  "Read some dates from a file"
  [path]
  (use 'clojure.java.io)
  (with-open [rdr (reader path)]
    (doseq [line (line-seq rdr)]
      (let [date-str (first (split line #" "))]
        (println (clj-time.format/parse date-str))))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (read-dates "expected_head.txt"))
