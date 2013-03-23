(defproject {{name}} "0.1.0-SNAPSHOT"
            :description "FIXME: write this!"
            :plugins [[lein-ring "0.8.3"]]
            :dependencies [
                        [org.clojure/clojure "1.5.1"]
            			[ring-cors/ring-cors "0.1.0"]
                        [ring "1.2.0-beta1"]
                        [clabango "0.5"]
                        [lib-noir "0.4.9"]
                 		[compojure "1.1.5"]
            ]
			:ring {:handler {{name}}.server/handler
            ; if you have friends
            ; :ring {:handler {{name}}.authserver/handler
         	:init    {{name}}.server/init
            :destroy {{name}}.server/destroy}
            :main {{name}}.server)