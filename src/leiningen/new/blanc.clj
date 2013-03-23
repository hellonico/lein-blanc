(ns leiningen.new.blanc
  (:require [leiningen.new.features :as features])
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
             ["ring.sh" (render "core/ring.sh")]
             ["project.clj" (render "core/project.clj" data)]
             [".gitignore" (render "core/gitignore" data)]
             ["README.md" (render "core/README.md" data)]
             ["src/{{sanitized}}/server.clj" (render "core/server.clj" data)]
             ["resources/public/css/reset.css" (render "core/reset.css" data)]
             ["src/{{sanitized}}/html/index.html" (render "clabango/template.html")]
             ["src/{{sanitized}}/authserver.clj" (render "friends/authserver.clj" data)]
             "resources/public/js"
             "resources/public/img"
             "test/{{sanitized}}"
             "src/{{sanitized}}/html"
             "test/{{sanitized}}"
             ]
             (features/include-features)
             ))
    (features/inject-dependencies)
    (features/inject-routes)
))