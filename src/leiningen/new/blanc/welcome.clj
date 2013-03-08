(ns {{name}}.views.welcome
  (:require [{{name}}.views.common :as common])
  (:use [noir.core :only [defpage]]))

(defpage "/welcome" []
         (common/layout
           [:p "Welcome to {{name}}"]))

(defpage "/" []
	 (clojure.java.io/resource "{{sanitized}}/html/bootstrap.html"))