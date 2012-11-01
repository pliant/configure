(ns pliant.configure.sniff)


(defn resource
  "Gets the input stream of a file on the classpath"
  [url]
   (-> (Thread/currentThread)
               (.getContextClassLoader)
               (.getResourceAsStream url)))


(defn sniff-env
  "Looks for a value in the environment variables."
  [k]
  (System/getenv (name k)))


(defn sniff-sys
  "Looks for a value in the system properties."
  [k]
  (System/getProperty (name k)))


(defn sniff-jndi
  "Looks for a value in the JNDI Naming context"
  [k]
  (try
    (.lookup (javax.naming.InitialContext.) (name k))
    (catch Exception e nil)))


(defn sniff-jndi-prefixed
  "Looks for a value in the JNDI Naming context"
  [k]
  (try
    (.lookup (javax.naming.InitialContext.) (name "java:comp/env/" k))
    (catch Exception e nil)))


(defn sniff-content
  "Looks for content in a file on the classpath."
  [k]
  (try 
    (slurp (resource (name k)))
    (catch Exception e nil)))


(defn sniff
  "Looks for a value identified by a key in the context of a sniffer."
  [k]
  (first 
    (remove nil?
      ((juxt sniff-env sniff-sys sniff-content sniff-jndi sniff-jndi-prefixed) k))))
