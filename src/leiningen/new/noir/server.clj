(ns {{name}}.server
  (:require [noir.server :as server]))

(server/load-views-ns '{{name}}.views)

(def handler (server/gen-handler {:mode :dev
                                  :ns '{{name}} }))

