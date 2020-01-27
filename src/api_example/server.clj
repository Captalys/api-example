(ns api-example.server
  (:require [clojure.tools.logging :as log]
            [mount.core :as mount])
  (:gen-class))

(defn -main []
  "Entry point function to start the whole application."
  (mount/start)
  (log/info ::api.flying))
