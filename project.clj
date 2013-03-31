(defproject pliant/configure "0.1.0"
  :description "Provides a simple library for adding flexible and encryptable configuration to your project."
  
  :url "https://github.com/pliant/process"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  
  :source-paths ["src/clj"]
  ;;:java-source-paths ["src/java"]
  :test-paths ["test/clj"]
  
  ;; Keep java source and project definition out of the artifact
  :jar-exclusions [#"^\." #"^*\/\." #"\.java$" #"project\.clj"]
  
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [commons-codec "1.6"]])

