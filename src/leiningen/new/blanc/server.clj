(ns {{name}}.server
  (:use compojure.core)
  (:use [ring.middleware.resource])
  (:require [noir.response :as resp])
  (:require [noir.util.middleware :as middleware])
  (:require [compojure.route :as route]))

(defroutes app
  (GET "/" [] "<h1>Hello World!</h1>")
  (GET "/json/:id" [id] (resp/json {:name id}))
  (GET "/angular" [] (slurp (clojure.java.io/resource "{{name}}/html/angular.html")))
  (GET "/bootstrap" [] (slurp (clojure.java.io/resource "{{name}}/html/bootstrap.html")))
  (GET "/headers" [] {:status 200 :headers {"Content-Type" "text/html"} :body "<h1>Hello with headers</h1>"})
  (POST "/post" {params :body} (prn "params:" (slurp params)))
  (route/not-found "<h1>Page not found</h1>"))

(defn init[]
  (println "{{name}} started successfully..."))

(defn destroy[]
  (println "shutting down..."))

(def handler (-> app
      (wrap-resource "public")))