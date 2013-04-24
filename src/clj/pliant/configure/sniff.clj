(ns pliant.configure.sniff
  (:require [pliant.configure.resources :as resources]))


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
  (resources/with-resource (name k) slurp))


(defn sniff
  "Looks for a value identified by a key in the context of a sniffer."
  [k]
  (first 
    (remove nil?
      ((juxt sniff-env sniff-sys sniff-content sniff-jndi sniff-jndi-prefixed) k))))
