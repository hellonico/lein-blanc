(ns leiningen.new.dependency-injector
  (:use clojure.pprint)
  (:require [clojure.java.io :as io]))

(defn- read-file [filename]
  (with-open [r (java.io.PushbackReader.
                  (io/reader filename))]
    (binding [*read-eval* false]
      (loop [exprs []]
        (if-let [expr (read r nil nil)]
          (recur (conj exprs expr))
          exprs)))))

(defn- to-project [f name version m]
  (->> m
    (reduce
      (fn [params param] (into params param))
      [f name version])
    (seq)))

(defn- pprint-code [code]
  (with-out-str (with-pprint-dispatch code-dispatch (pprint code))))

(defn- write-project [filename f name version m]
  (spit filename (pprint-code (to-project f name version m))))

(defn- update-item-list
  "filename is path to project.clj
type is the key to update
items are the items which will be appended to the value at the key
the value being updated is expected to be a collection"
  [filename type items]
  (let [[projectdef name version & more] (first (read-file filename))
        project-map (apply hash-map more)]
    (write-project filename projectdef name version
                   (update-in project-map [type]
                              #(if % (into % items) (vec items))))))

(defn add-to-project [filename k v]
  (let [[f name version & more] (first (read-file filename))
        project-map (apply hash-map more)]
    (write-project filename f name version
                   (assoc project-map k v))))

(defn add-dependencies [filename & dependencies]
  (update-item-list filename :dependencies dependencies))

(defn add-plugins [filename & plugins]
  (update-item-list filename :plugins plugins))

(defn add-to-ns [filename handler-fn & libs]
  (let [file (read-file filename)]
    (with-open [wrt (io/writer filename)]
      (doseq [expr file]
        (.write wrt
          (pprint-code (handler-fn expr)))
        (.write wrt "\n")))))

(defn add-required [filename & requires]
  (add-to-ns
    filename
    (fn [expr]
      (if (= 'ns (first expr))
        (clojure.walk/prewalk
          (fn [x]
            (if (and (coll? x) (= (first x) :require))
              (into x requires) x))
          expr)
        expr))))

(defn add-routes [filename & routes]
  (add-to-ns
    filename
    (fn [expr]
      (if (= 'all-routes (second expr))
        (list 'def 'all-routes (into (vec routes) (last expr)))
        expr))))

(defn set-lein-version [filename version]
  (spit filename
        (let [project-str (.trim (slurp filename))
              length (dec (count project-str))]
          (str (.substring project-str 0 length)
               "\n :min-lein-version \"" version "\")"))))