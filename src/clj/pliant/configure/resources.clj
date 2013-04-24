(ns pliant.configure.resources)

(defn classloader
  []
  (.getContextClassLoader (Thread/currentThread)))

(defn resource
  "Gets the input stream of a file located at the given path within the classpath."
  ([path]
   (resource path (classloader)))
  ([path ^ClassLoader classLoader]
   (.getResourceAsStream classLoader path)))

(defn resources
  "Returns a sequence of URLs for file(s) located at the given path within the classpath."
  ([path] (resources path (classloader)))
  ([path ^ClassLoader classLoader]
    (if-let [enum (.getResources classLoader path)]
      (enumeration-seq enum))))

(defn with-resource
  "Gets the stream of a resource on the classpath and passes it to the gobbler function.  Will close the stream if not already closed."
  ([path gobbler]
    (with-resource path gobbler (classloader)))
  ([path gobbler loader]
    (if-let [stream (resource path loader)]
      (with-open [^java.io.InputStream s stream]
        (gobbler s)))))

(defn with-resources
  "Gets the URLs of a resource(s) on the classpath and passes each to the gobbler function. 
   Will close the streams if not already closed by the gobbler."
  ([path gobbler]
    (with-resources path gobbler (classloader)))
  ([path gobbler loader]
    (if-let [urls (resources path loader)]
      (doall 
        (map (fn [^java.net.URL url]
               (with-open [^java.io.InputStream stream (.openStream url)]
                 (gobbler stream))) urls)))))
