(ns api-example.migrations
  (:require [api-example.services.config :refer [config]]
            [migratus.core :as migratus]))

(def migration-cfg {:store :database
                    :migration-dir "migrations/"
                    :init-in-transaction? false
                    :migration-table-name "migrations"
                    :db (:postgresql config)})

(defn create-migrations
  "Create two migration files, UP and DOWN, these code need to be written manually.

  :filename   prefix-name for the files to be created"
  [filename]
  (migratus/create migration-cfg filename))

(defn apply-migrations
  "Apply all the pending migrations in the connected database"
  []
  (migratus/migrate migration-cfg))


(defn rollback-migrations
  "Rollback all the migrations done previously"
  []
  (migratus/rollback migration-cfg))
