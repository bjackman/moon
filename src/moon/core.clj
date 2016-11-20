(ns moon.core
  (:require [clj-time.core :refer [date-time now year month day]])
  (:require [clj-time.format])
  (:require [clojure.java.io :refer [reader]])
  (:require [clojure.string :refer [split]])
  (:gen-class))

(let [maybe-sub-19 (fn [x] (if (> x 9) (- x 10) x))]
  (-> 10
      maybe-sub-19))

(defn negative-mod
  "Modulo, but if the input is negative so is the output"
  [x m]
  (if (< x 0) (- (mod (- x) m)) (mod x m)))

;; This approximation isn't very good (often out by 3 days).
;; Don't know if that's because of the algo or because of my shitty code.
(defn conway-moon-age
  "John Horton Conway's in-your-head algo for the age of the moon.
  Dates must be in 20th or 21st century.
  http://www.faqs.org/faqs/astronomy/faq/part3/section-15.html"
  [date]
  (let [maybe-sub-19 (fn [x] (if (> x 9) (- x 19) x))
        month* (month date)
        month-key (if (#{1 2} month*) (+ 2 month*) month*)
        century-key (if (= (quot (year date) 100) 19) 4 8)]
    (-> (year date)
        (mod 100)
        (mod 19)
        maybe-sub-19
        (* 11)
        (negative-mod 30)
        (+ (day date))
        (+ month-key)
        (- century-key)
        (mod 30))))

(def date-fmt (clj-time.format/formatters :date))

(defn read-dates
  "Read some dates from a file"
  [path]
  (with-open [rdr (reader path)]
    (doseq [line (line-seq rdr)]
      (let [parts (split line #" ")
            date (clj-time.format/parse (first parts))
            expected-age (Double/parseDouble (last parts))
            estimated-age (conway-moon-age date)]
        ;; How do i modular
        (when (and (> (Math/abs (- estimated-age expected-age)) 3)
                   (< (Math/abs (- estimated-age expected-age)) 27))
          (println (format "Failure at %s (%d vs %f)"
                           date estimated-age expected-age)))))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (read-dates "expected.txt"))
