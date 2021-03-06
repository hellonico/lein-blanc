(defproject {{name}} "0.1.0-SNAPSHOT"
            :description "FIXME: write this!"
            :plugins [[lein-ring "0.8.3"]]
            :min-lein-version "2.0.0"
            :production {:misc "configuration" ; app-specific stuff
             :offline true
             :mirrors {#"central|clojars"
                       "http://s3pository.herokuapp.com/clojure"}}
            :dependencies [
                        [org.clojure/clojure "1.5.1"]
                        [ring-cors/ring-cors "0.1.0"]
                        [ring "1.2.0-beta1"]
                        [clabango "0.5"]
                        [lib-noir "0.4.9"]
                        [compojure "1.1.5"]
                        [com.cemerick/friend "0.1.4"]
                        [friend-oauth2 "0.0.3"]
            ]
            :ring {:handler {{name}}.server/handler
            ; comment the below for friends demo
            ; :ring { :handler {{name}}.authserver/app
            :init    {{name}}.server/init
            :destroy {{name}}.server/destroy
            }
            :main {{name}}.server)