(ns api-example.services.database
  (:require [mount.core :as mount]
            [next.jdbc :as jdbc]
            [api-example.services.config :refer [config]]))

(mount/defstate datasource
  :start (jdbc/get-datasource (:postgresql config))
  :stop identity)
