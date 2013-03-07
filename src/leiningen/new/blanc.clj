(ns leiningen.new.blanc
  (:use leiningen.new.templates))

(def render (renderer "blanc"))

(defn blanc
  "A skeleton Noir project."
  [name]
  (let [data {:name name
              :sanitized (sanitize name)}]
    (println "Generating a lovely new Noir project named" (str name "..."))
    (->files data
             ; bootstrap
             ["project.clj" (render "project.clj" data)]
             [".gitignore" (render "gitignore" data)]
             ["README.md" (render "README.md" data)]
             ["src/{{sanitized}}/server.clj" (render "server.clj" data)]
             ["src/{{sanitized}}/views/welcome.clj" (render "welcome.clj" data)]
             ["src/{{sanitized}}/views/common.clj" (render "common.clj" data)]
             ["resources/public/css/reset.css" (render "reset.css" data)]
             "resources/public/js"
             "resources/public/app"
             "resources/public/img"
             "src/{{sanitized}}/models"
             "src/{{sanitized}}/html"
             "test/{{sanitized}}"
             ["resources/public/js/bootstrap.min.js" (render "bootstrap/js/bootstrap.min.js")]
             ["resources/public/img/glyphicons-halflings.png" (render "bootstrap/img/glyphicons-halflings.png")]
             ["resources/public/img/glyphicons-halflings-white.png" (render "bootstrap/img/glyphicons-halflings-white.png")]
             ["resources/public/css/bootstrap-responsive.min.css" (render "bootstrap/css/bootstrap-responsive.min.css")]
             ["resources/public/css/bootstrap.min.css" (render "bootstrap/css/bootstrap.min.css")]
             ["src/{{sanitized}}/html/bootstrap.html" (render "bootstrap.html")]
             ["src/{{sanitized}}/html/angular.html" (render "angular.html")]
             ["resources/public/app/todo.css" (render "app/todo.css")]
             ["resources/public/app/todo.js" (render "app/todo.js")]
             )))