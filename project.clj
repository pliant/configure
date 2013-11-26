(defproject pliant/configure "0.1.3-SNAPSHOT"
  :description "Provides a small Clojure library that adds encryptable configuration and runtime resource loading to your software application or software product."
  
  :url "https://github.com/pliant/configure"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  
  :source-paths ["src/clj"]
  ;;:java-source-paths ["src/java"]
  :test-paths ["test/clj"]
  
  ;; Keep java source and project definition out of the artifact
  :jar-exclusions [#"^\." #"^*\/\." #"\.java$" #"project\.clj"]
  
  :signing {:gpg-key "B42493D5"}
  
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [commons-codec "1.6"]])
