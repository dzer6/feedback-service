(ns feedback.service.core
  (:gen-class)
  (:require
    [org.httpkit.server    :as http-kit-server]
    [compojure.core        :refer (defroutes GET POST)]
    [compojure.route       :as route]
    [compojure.handler     :as handler]
    [ring.util.response    :as rur]
    [clojure.tools.logging :as log]
    [environ.core          :as e]))

(def startup-params {:ip (e/env "OPENSHIFT_CLOJURE_IP")
                     :port (Integer/parseInt (e/env "OPENSHIFT_CLOJURE_PORT")) })

(defroutes my-routes
  (GET  "/"      req "123"))

(def my-ring-handler
  (-> my-routes
      handler/site))

(defn -main [& args]
  (log/info "startup params =" startup-params)
  (http-kit-server/run-server (var my-ring-handler) startup-params))
