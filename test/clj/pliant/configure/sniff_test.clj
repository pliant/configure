(ns pliant.configure.sniff-test
  (:use [pliant.configure.sniff])
  (:use [clojure.test]))

(deftest test-sniff 
  (is (= "PASSKEY" (sniff-content "KEY")) "Sniffing content on the classpath failed")
  (is (= "PASSKEY" (sniff "KEY")) "Sniffing content on the classpath failed with global sniff")
  (is (nil? (sniff "LKJDLSFJLKSDJFKLSDJFJSDLJFJSDLJDFS")) "Global sniff found value it shouldn't have: LKJDLSFJLKSDJFKLSDJFJSDLJFJSDLJDFS"))
