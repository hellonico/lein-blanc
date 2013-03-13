(ns {{name}}.server
  (:use compojure.core)
  (:require [noir.response :as resp])
  (:require [noir.util.middleware :as middleware])
  (:require [compojure.route :as route]))

(defroutes app
  (GET "/" [] "<h1>Hello World!</h1>")
  (GET "/json/:id" [id] (resp/json {:name id}))
  (GET "/headers" [] {:status 200 :headers {"Content-Type" "text/html"} :body "<h1>Hello with headers</h1>"})
  (POST "/post" {params :body} (prn "params:" (slurp params)))
  (route/not-found "<h1>Page not found</h1>"))

(defn init[]
  (println "test3 started successfully..."))

(defn destroy[]
  (println "shutting down..."))

; (def handler (-> app
;              middleware/app-handler
;              ;;add your middlewares here
;              ))
(def handler app)