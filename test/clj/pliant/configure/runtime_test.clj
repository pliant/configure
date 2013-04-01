(ns pliant.configure.runtime-test
  (:use [pliant.configure.runtime])
  (:use [clojure.test]))

(load-resources "pliant/bootstrap/LoadResourceWithNamespace.txt")
(load-resources "pliant/bootstrap/LoadResourceWithOtherNamespace.txt")
(load-resources "pliant/bootstrap/LoadResourceReferNamespace.txt")

(deftest test-with-namespace 
  (is (find-ns  'pliant.bootstrap.LoadResourceWithNamespace) "Could not find the pliant.bootstrap.LoadResourceWithNamespace namespace.")
  (is (find-var 'pliant.bootstrap.LoadResourceWithNamespace/test-var) "Could not find the pliant.bootstrap.LoadResourceWithNamespace/test-var variable.")
  (is (find-var 'pliant.bootstrap.LoadResourceWithNamespace/test-fn) "Could not find the pliant.bootstrap.LoadResourceWithNamespace/test-fn function."))

(deftest test-with-other-namespace 
  (is (find-ns  'pliant.bootstrap.LoadResourceWithNamespace) "Could not find the pliant.bootstrap.LoadResourceOther namespace.")
  (is (find-var 'pliant.bootstrap.LoadResourceWithNamespace/test-var) "Could not find the pliant.bootstrap.LoadResourceOther/test-var variable.")
  (is (find-var 'pliant.bootstrap.LoadResourceWithNamespace/test-fn) "Could not find the pliant.bootstrap.LoadResourceOther/test-fn function."))

(deftest test-refer-namespace 
  (is (find-ns  'pliant.bootstrap.refer) "Could not find the pliant.bootstrap.refer namespace.")
  (is (find-var 'pliant.bootstrap.refer/test-var) "Could not find the pliant.bootstrap.refer/test-var variable.")
  (is (find-var 'pliant.bootstrap.refer/test-fn) "Could not find the pliant.bootstrap.refer/test-fn function."))
