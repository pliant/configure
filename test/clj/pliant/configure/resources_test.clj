(ns pliant.configure.resources-test
  (:use [pliant.configure.resources])
  (:use [clojure.test]))


(deftest test-resource 
  (with-open [good-resource (resource "pliant/bootstrap/LoadResourceWithNamespace.txt")]
    (is (not (nil? good-resource)) 
        "Unable to find the resource 'pliant/bootstrap/LoadResourceWithNamespace.txt' on the classpath."))
  (is (nil? (resource "nope/derp/nohere.txt")) 
      "Found a resource on the classpath where it should not.  Check your sources for 'nope/derp/nohere.txt'."))

(deftest test-resources
  (let [urls (resources "pliant/bootstrap/LoadResourceWithNamespace.txt")]
    (is (not (nil? urls)) 
        "Unable to find the resources at 'pliant/bootstrap/LoadResourceWithNamespace.txt' on the classpath.")
    (is (= 1 (count urls)) "Did not respond with correct number of resources.")
    (is (= java.net.URL (type (first urls))) "Did not respond with java.net.URL based resources."))
  (is (nil? (resources "nope/derp/nohere.txt")) 
      "Found resources on the classpath where it should not.  Check your sources for 'nope/derp/nohere.txt'."))

(deftest test-with-resource 
  (let [content (with-resource "pliant/bootstrap/LoadResourceWithNamespace.txt" slurp)]
    (is (not (nil? content)) 
        "slurp is not bringing back any content from 'pliant/bootstrap/LoadResourceWithNamespace.txt'.")))

(deftest test-with-resources
  (let [content (with-resources "pliant/bootstrap/LoadResourceWithNamespace.txt" slurp)]
    (is (not (nil? content)) 
        "slurp is not bringing back any content from 'pliant/bootstrap/LoadResourceWithNamespace.txt'.")
    (is (= 1 (count content)) "with-resources did not return a sequence with content.")
    (is (not (nil? (first content))) "with-resources did not have content in the first item of the sequence.")))
