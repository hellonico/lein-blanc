(defproject {{name}} "0.1.0-SNAPSHOT"
            :description "FIXME: write this!"
            :dependencies [[org.clojure/clojure "1.5.0-RC3"]
                           [noir "1.3.0-beta3"]
                           [lein-ring "0.8.3"]]
			:ring {:handler {{name}}.server/handler}
            :main {{name}}.server)

