(ns {{name}}.server
  (:use compojure.core)
  (:use [ring.middleware.resource])
  (:require [clabango.parser :refer [render-file]])
  (:require [noir.response :as resp])
  (:require [noir.util.middleware :as middleware])
  (:require [compojure.route :as route]))

(defroutes app
  (GET "/" [] "<h1>Hello World!</h1>")
  (GET "/json/:id" [id] (resp/json {:name id}))
  (GET "/headers" [] {:status 200 :headers {"Content-Type" "text/html"} :body "<h1>Hello with headers</h1>"})
  (POST "/post" {params :body} (prn "params:" (slurp params)))
  (GET "/template/:id" [id] (render-file "{{name}}/templates/index.html" {:greeting (str "Hey!" id)}))
  (route/resources "/")
  (route/not-found "<h1>Page not found</h1>"))

(def all-routes [app])

(defn init[]
  (println "{{name}} started successfully..."))

(defn destroy[]
  (println "shutting down..."))


(def handler (middleware/app-handler all-routes))