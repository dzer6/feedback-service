(ns feedback.service.core
  (:gen-class)
  (:use feedback.service.middleware.json)
  (:require
    [org.httpkit.server :as http-kit-server]
    [ring.middleware.cors :as rc]
    [compojure.core :refer (defroutes)]
    [compojure.handler :as handler]
    [clojure.tools.logging :as log]
    [feedback.service.controller.mail :as mail]))

(def startup-params {:ip (or (System/getenv "OPENSHIFT_CLOJURE_HTTP_IP") "localhost")
                     :port (or (-> "OPENSHIFT_CLOJURE_HTTP_PORT" System/getenv read-string) 8080)})

(def origin (-> "FEEDBACK_SERVICE_ORIGIN_HOST" System/getenv java.util.regex.Pattern/compile))

(defroutes my-routes
  (JPOST  "/send" request mail/handle))

(def my-ring-handler
  (-> my-routes
      handler/site
      (rc/wrap-cors :access-control-allow-origin [origin] :access-control-allow-methods [:get :put :post :delete])))

(defn -main [& args]
  (log/info "startup params =" startup-params)
  (http-kit-server/run-server (var my-ring-handler) startup-params))
