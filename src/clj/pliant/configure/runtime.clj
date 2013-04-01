(ns pliant.configure.runtime)

;; There seems to be some question as to whether the (clojure.lang.RT/baseLoader) classloader or the 
;; (.getContextClassLoader (Thread/currentThread)) classloader to get resources.  It is probably based 
;; on the context which resources are being requested.  Will use the context classloader from the 
;; current thread for now, as that is what is used in the clojure load function.
(defn load-resources
  "Provides a way to bootstrap all of the resources matching a specific path into the clojure compiler."
  ([path] (load-resources path (.getContextClassLoader (Thread/currentThread))))
  ([path classLoader]
    (let [resources (enumeration-seq (.getResources classLoader path))]
      (doseq [url resources]
        (load-reader (java.io.InputStreamReader. (.openStream url)))))))
