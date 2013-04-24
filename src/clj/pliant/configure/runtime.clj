(ns pliant.configure.runtime
  (:require [pliant.configure.resources :as resources]))

;; There seems to be some question as to whether the (clojure.lang.RT/baseLoader) classloader or the 
;; (.getContextClassLoader (Thread/currentThread)) classloader to get resources.  It is probably based 
;; on the context which resources are being requested.  Will use the context classloader from the 
;; current thread for now, as that is what is used in the clojure load function.
(defn load-resources
  "Provides a way to bootstrap all of the resources matching a specific path into the clojure compiler."
  ([path] (load-resources path (resources/classloader)))
  ([path classloader]
    (resources/with-resources path #(load-reader (java.io.InputStreamReader. %)) classloader)))