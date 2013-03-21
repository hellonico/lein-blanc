(ns leiningen.new.features
  (:use leiningen.new.templates)
	(:use leiningen.new.dependency-injector))

;;; Generic 
(def features (atom nil))
(def projectname (atom nil))
(def render (renderer "blanc"))

(defn init-features [pname parameters]
  (reset! projectname pname)
	(reset! features parameters))

(defmulti add-feature keyword)
(defmulti post-process (fn [feature _] (keyword feature)))
(defmulti new-routes keyword)

(defmethod add-feature :default [feature]
 (throw (new Exception (str "unrecognized feature: " (name feature)))))
(defmethod post-process :default [_ _])
(defmethod new-routes :default [feature])

(defn inject-dependencies []
  (let [project-file (str @projectname java.io.File/separator "project.clj")]
    (doseq [feature @features]
      (post-process feature project-file))))

(defn inject-routes[]
  (let [route-file (str @projectname "/src/" @projectname "/server.clj")]
    (doseq [feature @features]
      (apply add-routes route-file (new-routes feature)))))


(defn include-features []
  (mapcat add-feature @features))

;;;
;;; Features list
;;;

;; H2
(defmethod add-feature :+h2 [_]
  [
   ; ["src/log4j.xml" (render "dbs/log4j.xml")]
   ; ["src/{{sanitized}}/models/db.clj" (render "dbs/db.clj")]
   ; ["src/{{sanitized}}/models/schema.clj" (render "dbs/h2_schema.clj")]])
   ])
(defmethod post-process :+h2 [_ project-file]
  (add-dependencies project-file
     ['com.h2database/h2 "1.3.170"]))

;; BOOTSTRAP
(defmethod add-feature :+bootstrap [_]
 [
 ["resources/public/js/bootstrap.min.js" (render "bootstrap/js/bootstrap.min.js")]
 ["resources/public/img/glyphicons-halflings.png" (render "bootstrap/img/glyphicons-halflings.png")]
 ["resources/public/img/glyphicons-halflings-white.png" (render "bootstrap/img/glyphicons-halflings-white.png")]
 ["resources/public/css/bootstrap-responsive.min.css" (render "bootstrap/css/bootstrap-responsive.min.css")]
 ["resources/public/css/bootstrap.min.css" (render "bootstrap/css/bootstrap.min.css")]
 ["resources/public/html/bootstrap.html" (render "bootstrap/bootstrap.html")]])
; (defmethod post-process :+bootstrap [_ _])
(defmethod new-routes :+bootstrap [_]
     [
     '(GET "/bootstrap" [] (resp/redirect "/html/bootstrap.html"))
     ])

(defmethod add-feature :+angular [_]
  [["src/html/angular.html" (render "angular/angular.html")]
  ["resources/public/app/todo.css" (render "angular/todo.css")]
  ["resources/public/app/todo.js" (render "angular/todo.js")]])
; (defmethod post-process :+angular [_ _])
(defmethod new-routes :+angular [_]
  [
    '(GET "/angular" [] (slurp (clojure.java.io/resource "html/angular.html")))
  ])

;; GOOGLE MAPS
