(ns feedback.service.core
  (:gen-class)
  (:use feedback.service.middleware.json)
  (:require
    [org.httpkit.server :as http-kit-server]
    [compojure.core :refer (defroutes)]
    [compojure.route :as route]
    [compojure.handler :as handler]
    [ring.util.response :as rur]
    [clojure.tools.logging :as log]
    [feedback.service.controller.mail :as mail]))

(def startup-params {:ip (or (System/getenv "OPENSHIFT_CLOJURE_HTTP_IP") "localhost")
                     :port (or (-> "OPENSHIFT_CLOJURE_HTTP_PORT" System/getenv read-string) 8080)})

(defroutes my-routes
  (JPOST  "/send" request mail/send))

(def my-ring-handler
  (-> my-routes
      handler/site))

(defn -main [& args]
  (log/info "startup params =" startup-params)
  (http-kit-server/run-server (var my-ring-handler) startup-params))
