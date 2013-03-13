(ns {{name}}.server
  (:use compojure.core)
  (:require [noir.util.middleware :as middleware])
  (:require [compojure.route :as route]))

(defroutes app
  (GET "/" [] "<h1>Hello World!</h1>")
  (route/not-found "<h1>Page not found</h1>"))

(defn init[]
  (println "{{name}} started successfully..."))

(defn destroy[]
  (println "shutting down..."))

; (def handler (-> app
;              middleware/app-handler
;              ;;add your middlewares here
;              ))
(def handler app)