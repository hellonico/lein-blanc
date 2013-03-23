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
  [
  ["src/html/angular.html" (render "angular/angular.html")]
  ["resources/public/app/todo.css" (render "angular/todo.css")]
  ["resources/public/app/todo.js" (render "angular/todo.js")]])
; (defmethod post-process :+angular [_ _])
(defmethod new-routes :+angular [_]
  [
    '(GET "/angular" [] (slurp (clojure.java.io/resource "html/angular.html")))
  ])

;; GOOGLE MAPS
(defmethod add-feature :+maps [_]
  [
  ["resources/public/js/bootstrap.min.js" (render "googlemaps/ui/min/jquery.ui.map.full.min.js")]
  ["resources/public/html/maps.json.html" (render "googlemaps/maps.json.html")]
  ["resources/public/css/960/min/960.css" (render "googlemaps/demos/css/960/min/960.css")]
  ["resources/public/css/960/min/960_16_col.css" (render "googlemaps/demos/css/960/min/960_16_col.css")]
  ["resources/public/css/normalize/min/normalize.css" (render "googlemaps/demos/css/normalize/min/normalize.css")]
  ["resources/public/css/prettify/min/prettify.css" (render "googlemaps/demos/css/prettify/min/prettify.css")]
  ["resources/public/css/style.css" (render "googlemaps/demos/css/style.css")]
  ["resources/public/js/modernizr-2.0.6/modernizr.min.js" (render "googlemaps/demos/js/modernizr-2.0.6/modernizr.min.js")]
  ["resources/public/js/jquery-1.7.1/jquery.min.js" (render "googlemaps/demos/js/jquery-1.7.1/jquery.min.js")]
  ["resources/public/js/underscore-1.2.2/underscore.min.js" (render "googlemaps/demos/js/underscore-1.2.2/underscore.min.js")]
  ["resources/public/js/backbone-0.5.3/backbone.min.js" (render "googlemaps/demos/js/backbone-0.5.3/backbone.min.js")]
  ["resources/public/js/prettify/prettify.min.js" (render "googlemaps/demos/js/prettify/prettify.min.js")]
  ["resources/public/js/demo.js" (render "googlemaps/demos/js/demo.js")]
  ["resources/public/ui/jquery.ui.map.js" (render "googlemaps/ui/jquery.ui.map.js")]
    
  ]
)

(defmethod new-routes :+angular [_]
  [
    '(GET "/maps" [] (slurp (clojure.java.io/resource "html/maps.json.html")))
  ])

;; Gumby
(defmethod add-feature :+gumby [_]
  [
  ; CSS
  ["resources/public/css/gumby.css" (render "gumby/css/gumby.css")]
  ["resources/public/css/style.css" (render "gumby/css/style.css")]

  ; FONTS
  ["resources/public/fonts/icons/entypo.eot" (render "gumby/fonts/icons/entypo.eot")]
  ["resources/public/fonts/icons/entypo.ttf" (render "gumby/fonts/icons/entypo.ttf")]
  ["resources/public/fonts/icons/entypo.woff" (render "gumby/fonts/icons/entypo.woff")]

  ; IMG
  ["resources/public/img/gumby_mainlogo.png" (render "gumby/img/gumby_mainlogo.png")]
  ["resources/public/img/gumby_mainlogo@2x.png" (render "gumby/img/gumby_mainlogo@2x.png")]
  
  ; JS
  ["resources/public/js/gumby.min.js" (render "gumby/js/libs/gumby.min.js")]
  ["resources/public/js/jquery.min.js" (render "gumby/js/libs/jquery-1.8.3.min.js")]
  ["resources/public/js/modernizr.min.js" (render "gumby/js/libs/modernizr-2.6.2.min.js")]

  ["resources/public/js/main.js" (render "gumby/js/main.js")]
  ["resources/public/js/plugins.js" (render "gumby/js/plugins.js")]

  ; HTML
  ["resources/public/html/ui.html" (render "gumby/ui.html")]  

  ]
)
; friends
(defmethod add-feature :+friends [_]
  [
  ["src/{{sanitized}}/authserver.clj" (render "friends/authserver.clj" data)]
  ]
)

(defmethod post-process :+friends [_ project-file]
  (add-dependencies project-file
     [com.cemerick/friend "0.1.4"]))

; Raphael
(defmethod add-feature :+raphael [_]
  [
  ; HTML
  ["resources/public/html/piechart.html" (render "raphael/piechart.html")]  
  ["resources/public/html/raphael.html" (render "raphael/raphael.html")]  

  ; JS
  ["resources/public/js/raphael.js" (render "raphael/raphael.js")]
  ["resources/public/js/g.raphael-min.js" (render "raphael/g.raphael-min.js")]
  ["resources/public/js/g.pie-min.js" (render "raphael/g.pie-min.js")]
  ["resources/public/js/g.line-min.js" (render "raphael/g.line-min.js")]
  ["resources/public/js/g.dot-min.js" (render "raphael/g.dot-min.js")]
  ["resources/public/js/g.bar-min.js" (render "raphael/g.bar-min.js")]

  ; CSS
  ["resources/public/css/demo.css" (render "raphael/demo.css")]
  ["resources/public/css/demo-print.css" (render "raphael/demo-print.css")]
  ]
)