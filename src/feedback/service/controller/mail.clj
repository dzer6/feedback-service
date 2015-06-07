(ns feedback.service.controller.mail
  (:require
    [clojure.tools.logging :as log]
    [postal.core :as pst]
    [clojure.string :as s]))

(defn- send-letter-via-gmail [name email phone body]
  (pst/send-message ^{:host (System/getenv "FEEDBACK_SERVICE_MAIL_HOST")
                      :user (System/getenv "FEEDBACK_SERVICE_MAIL_USER")
                      :pass (System/getenv "FEEDBACK_SERVICE_MAIL_PASSWORD")
                      :ssl :yes!!!11}
                    {:from email
                     :to (System/getenv "FEEDBACK_SERVICE_MAIL_DESTINATION")
                     :subject (str "[Good Production Feedback Form] [" name "][" email "][" phone "]")
                     :body (str "From " name ":\nPhone:" phone "\n\n"  body)
                     :type "text/plain; charset=utf-8"}))

(defn handle [request]
  (log/info "send mail request =" request)
  (let [{:keys [name email phone body]} (:body request)
        phone (if (s/blank? phone) "None" phone)]
    (send-letter-via-gmail name email phone body)))