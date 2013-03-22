(defproject {{name}} "0.1.0-SNAPSHOT"
            :description "FIXME: write this!"
            :plugins [[lein-ring "0.8.3"]]
            :dependencies [[org.clojure/clojure "1.5.1"]
            			   [me.raynes/laser "1.1.1"]
            			   [ring-cors/ring-cors "0.1.0"]
                        [ring "1.2.0-beta1"]
                        [lib-noir "0.4.9"]
                 		   [compojure "1.1.5"]
                         ]
			:ring {:handler {{name}}.server/handler
         	:init    {{name}}.server/init
            :destroy {{name}}.server/destroy}
            :main {{name}}.server)