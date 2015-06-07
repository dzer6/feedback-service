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

(log/info "All environment variables:" (System/getenv))
(log/info "OPENSHIFT_CLOJURE_HTTP_IP" (System/getenv "OPENSHIFT_CLOJURE_HTTP_IP"))
(log/info "OPENSHIFT_CLOJURE_HTTP_PORT" (System/getenv "OPENSHIFT_CLOJURE_HTTP_PORT"))

(def startup-params {:ip (e/env :OPENSHIFT_CLOJURE_HTTP_IP)
                     :port (-> :OPENSHIFT_CLOJURE_HTTP_PORT e/env read-string) })

(defroutes my-routes
  (GET  "/"      req "123"))

(def my-ring-handler
  (-> my-routes
      handler/site))

(defn -main [& args]
  (log/info "startup params =" startup-params)
  (http-kit-server/run-server (var my-ring-handler) startup-params))
