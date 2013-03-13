(ns leiningen.new.blanc
  (:require [leiningen.new.blanc.dev.features :as features])
  (:use leiningen.new.templates))

(def render (renderer "blanc"))

(defn blanc
  "A skeleton Ring project."
  [name & parameters]
  (features/init-features name parameters)
  (let [data {:name name
              :sanitized (sanitize name)}]
    (println "Generating a new Web project named" (str name "..."))
    (apply (partial ->files data)
             (into 
             [
             ; core
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
             "test/{{sanitized}}"
             "src/{{sanitized}}/html"
             "test/{{sanitized}}"
             ["src/{{sanitized}}/html/angular.html" (render "angular.html")]
             ["resources/public/app/todo.css" (render "app/todo.css")]
             ["resources/public/app/todo.js" (render "app/todo.js")]
             ]
             (features/include-features)
             ))
    (features/inject-dependencies)
    ))