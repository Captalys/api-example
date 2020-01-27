(ns api-example.server
  (:require [clojure.tools.logging :as log]))

(defn -main []
  "Entry point function to start the whole application."
  (mount/start)
  (log/info ::api.flying))
