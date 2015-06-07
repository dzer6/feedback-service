(ns feedback.service.middleware.json ; https://github.com/shenfeng/rssminer/blob/master/src/rssminer/middleware.clj
  (:require [cheshire.core :as cheshire]
            [compojure.core :as cc]
            [clojure.tools.logging :as log]))

(def ^{:private true} json-resp-header {"Content-Type" "application/json; charset=utf-8"})

(defn create-response [resp]
  (if (some #(= :body (first %)) resp)
    (let [r {:status (or (:status resp) 200)
             :headers (merge json-resp-header (:headers resp))
             :body (-> resp :body cheshire/generate-string)}]
      (if (some #(= :session (first %)) resp)
        (assoc r :session (:session resp))
        r))
    {:status 200
     :headers json-resp-header
     :body (cheshire/generate-string resp)}))

(defn parse-body [req]
  (if-let [body (:body req)]
    (-> body
        .bytes
        String.
        (cheshire/parse-string true))))

(defn wrap-json [handler]
  (fn [req]
    (try
      (let [json-body (parse-body req)
            resp (handler (assoc req :body json-body))]
        (create-response resp))
      (catch Exception e
        (log/error e "API error request: " req)
        {:status 500
         :body {:error "Opps, an error occured"}}))))

(defmacro JPOST [path args handler]
  `(cc/POST ~path ~args (wrap-json ~handler)))

(defmacro JPUT [path args handler]
  `(cc/PUT ~path ~args (wrap-json ~handler)))

(defmacro JGET [path args handler]
  `(cc/GET ~path ~args (wrap-json ~handler)))

(defmacro JDELETE [path args handler]
  `(cc/DELETE ~path ~args (wrap-json ~handler)))

(defmacro JHEAD [path args handler]
  `(cc/HEAD ~path ~args (wrap-json ~handler)))

(defmacro JOPTIONS [path args handler]
  `(cc/OPTIONS ~path ~args (wrap-json ~handler)))

(defmacro JPATCH [path args handler]
  `(cc/PATCH ~path ~args (wrap-json ~handler)))

(defmacro JANY [path args handler]
  `(cc/ANY ~path ~args (wrap-json ~handler)))