(ns leiningen.new.blanc.dev.features
  (:use leiningen.new.templates)
	(:use leiningen.new.blanc.dev.dependency-injector))

;;; Generic 
(def features (atom nil))
(def projectname (atom nil))
(def render (renderer "blanc"))

(defn init-features [pname parameters]
  (reset! projectname pname)
	(reset! features parameters))

(defmulti add-feature keyword)
(defmulti post-process (fn [feature _] (keyword feature)))
(defmethod add-feature :default [feature]
 (throw (new Exception (str "unrecognized feature: " (name feature)))))
(defmethod post-process :default [_ _])

(defn inject-dependencies []
  (let [project-file (str @projectname java.io.File/separator "project.clj")]
    (doseq [feature @features]
      (post-process feature project-file))))

(defn include-features []
  (mapcat add-feature @features))

;;; Features list

(defmethod add-feature :+h2 [_]
  [["src/log4j.xml" (render "dbs/log4j.xml")]
   ["src/{{sanitized}}/models/db.clj" (render "dbs/db.clj")]
   ["src/{{sanitized}}/models/schema.clj" (render "dbs/h2_schema.clj")]])

(defmethod post-process :+h2 [_ project-file]
  (add-dependencies project-file
                         ['com.h2database/h2 "1.3.170"]))
