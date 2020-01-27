(defproject api-example "0.1.0-SNAPSHOT"
  :description "Provide some study around API building"
  :url "http://github.com/wandersoncferreira/api-example"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/tools.namespace "0.3.1"]
                 [org.clojure/tools.logging "0.5.0"]
                 [metosin/reitit "0.4.1"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [com.fasterxml.jackson.core/jackson-core "2.10.0"]
                 [metosin/spec-tools "0.10.1"]
                 [cheshire "5.9.0"]
                 [mount "0.1.9"]
                 [aero "1.1.3"]
                 [migratus "1.2.7"]]
  :main api-example.core
  :profiles {:dev {:plugins [[lein-ring "0.12.5"]]}}
  :repl-options {:init-ns user})
