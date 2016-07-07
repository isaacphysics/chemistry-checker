(ns server
  (:use [compojure.handler :only [site]]
        [compojure.core :only [defroutes POST]])
  (:require [org.httpkit.server :as server])
  (:import [org.isaacphysics.labs.chemistry.checker RunParser]))

(defroutes routes
  (POST "/check" [test target]
    (println "Checking" test "against" target)
    {:status 200
     :body (RunParser/check test target)
     :headers {"Content-Type" "application/json"}})
;  (POST "/parse" [test]
;    (println "Parsing" test)
;    {:status 200
;     :body (RunParser/parseFromString test)
;     :headers {"Content-Type" "application/json"}})
     )

(print "Starting server...")
(server/run-server (site #'routes) {:port 80})
(println " done.")

(println "Waiting forever")
@(promise)