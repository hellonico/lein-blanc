(defproject {{name}} "0.1.0-SNAPSHOT"
            :description "FIXME: write this!"
            :plugins [[lein-ring "0.8.3"]]
            :dependencies [[org.clojure/clojure "1.5.0-RC3"]
            			   [me.raynes/laser "1.1.1"]
                           [noir "1.3.0-beta3"]]
			:ring {:handler {{name}}.server/handler}
            :main {{name}}.server)