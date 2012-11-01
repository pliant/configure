(ns pliant.configure.props
  (:use [pliant.configure.codec :only [decrypter decode]])
  (:use [pliant.configure.sniff :only [resource]]))


(defn slurp-props
  "Loads the key/values found in a properties file found on the classpath into a map"
  [url]
  (try
    (let [props (into {} (doto (java.util.Properties.)(.load (resource url))))]
      (concat props (for [[k v] props] [(keyword k) v])))
    (catch Exception e {})))


(defn- self
  "Just a bogus function to return what is passed"
  [string]
  string)


(defn slurp-config
  ""
  ([url]
    (slurp-config url nil {}))

  ([url passkey]
    (slurp-config url passkey {}))

  ([url passkey options]
    (let [props (slurp-props url)
          decr (if (nil? passkey) self (decrypter passkey options))]
      (into {} (for [[k v] props] [k (decode v decr)]))
      )))