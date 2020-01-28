(ns api-example.core
  (:require [api-example.services.database :refer [datasource]]
            [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql]))

(def data-example [{:id 1 :values 10 :letters "coding"}
                   {:id 2 :values 390 :letters "about"}
                   {:id 3 :values 112 :letters "talking"}
                   {:id 4 :values 23 :letters "are"}
                   {:id 5 :values 2 :letters "we"}
                   {:id 6 :values 0 :letters "now"}])

(defn insert-data->example-table
  "Insert multiple data into the database using a transaction to rollback if error happens."
  [data]
  (jdbc/with-transaction [tx datasource]
    (let [columns (keys (first data))
          values (map vals data)]
      (sql/insert-multi! tx "example_real" columns values))))

(defn query-data-by-id
  "Query database by id."
  [id]
  (sql/get-by-id datasource "example_real" id))
