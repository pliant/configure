(ns pliant.configure.props-test
  (:use [pliant.configure.props])
  (:use [pliant.configure.sniff])
  (:use [clojure.test]))


(deftest test-slurp-props 
  (is (> (count (slurp-props "test.props")) 0) "Unable to load test.props file from classpath."))


(deftest test-slurp-config 
  (let [config (slurp-config "test.props")]
  (is (> (count config) 0) "Unable to load test.props file from classpath.")
  (is (= "value1" (config "key_1")) "Could not find the correct value for key_1")
  (is (not (= "value2" (config "key-2"))) "Could not find the correct value for key-2")
  (is (not (= "value3" (config "key.3"))) "Could not find the correct value for key.3")
  (is (nil? (config "unknown-key")) "Could not find the correct value for unknown-key")))


(deftest test-slurp-config-decrypt
  (let [config (slurp-config "test.props" "PASSKEY")]
  (is (> (count config) 0) "Unable to load test.props file from classpath.")
  (is (= "value1" (config "key_1")) "Could not find the correct value for key_1")
  (is (= "value2" (config "key-2")) "Could not find the correct value for key-2")
  (is (= "value3" (config "key.3")) "Could not find the correct value for key.3")
  (is (nil? (config "unknown-key")) "Could not find the correct value for unknown-key")))


(deftest test-slurp-config-decrypt-sniff
  (let [config (slurp-config "test.props" (sniff "KEY"))]
  (is (> (count config) 0) "Unable to load test.props file from classpath.")
  (is (= "value1" (config "key_1")) "Could not find the correct value for key_1")
  (is (= "value2" (config "key-2")) "Could not find the correct value for key-2")
  (is (= "value3" (config "key.3")) "Could not find the correct value for key.3")
  (is (nil? (config "unknown-key")) "Could not find the correct value for unknown-key")))
