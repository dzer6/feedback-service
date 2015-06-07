(defproject feedback-service "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure            "1.7.0-RC1"]
                 [http-kit                       "2.1.19"]
                 [compojure                      "1.1.6"]
                 [ring                           "1.3.1"]
                 [ring-cors                      "0.1.7"]
                 [org.clojure/tools.logging      "0.2.6"]
                 [org.slf4j/slf4j-api            "1.7.7"]
                 [org.slf4j/log4j-over-slf4j     "1.7.7"]
                 [ch.qos.logback/logback-classic "1.1.2"]
                 [cheshire "5.3.1"]
                 [com.draines/postal "1.11.1"]]
  :main ^:skip-aot feedback.service.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
