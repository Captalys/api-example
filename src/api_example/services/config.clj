(ns api-example.services.config
  (:require [mount.core :as mount]
            [clojure.java.io :as io]
            [aero.core :refer [read-config]]))

(defn get-config!
  ([]
   (get-config! :default))
  ([profile]
   (-> "config.edn"
       io/resource
       (read-config {:profile profile}))))

(mount/defstate config
  :start (get-config! :production)
  :stop identity)
