(ns leiningen.new.blanc
  (:require [leiningen.new.blanc.dev.features :as features])
  (:use leiningen.new.templates))

(def render (renderer "blanc"))

(defn blanc
  "A skeleton Noir project."
  [name & parameters]
  (features/init-features name parameters)
  (let [data {:name name
              :sanitized (sanitize name)}]
    (println "Generating a lovely new Noir project named" (str name "..."))
    (features/include-features)
    (->files data
             ["project.clj" (render "project.clj" data)]
             [".gitignore" (render "gitignore" data)]
             ["README.md" (render "README.md" data)]
             ["src/{{sanitized}}/server.clj" (render "server.clj" data)]
             ["src/{{sanitized}}/views/welcome.clj" (render "welcome.clj" data)]
             ["src/{{sanitized}}/views/common.clj" (render "common.clj" data)]
             ["resources/public/css/reset.css" (render "reset.css" data)]
             "resources/public/js"
             "resources/public/img"
             "src/{{sanitized}}/models"
             "test/{{sanitized}}")
    (features/inject-dependencies)
    ))