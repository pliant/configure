(ns pliant.configure.codec-test
  (:use [pliant.configure.codec])
  (:use [clojure.test]))

(deftest test-byte-array? 
  (is (not (byte-array? "Nope" )) "Falsely reported a string as a byte array")
  (is (byte-array? (.getBytes "Yep")) "Falsely reported a byte array as not one"))

(deftest test-conversion
  (is (byte-array? (bytify "Yep")) "Bytify did not make a string into bytes.")
  (is (= (type (stringify (bytify "Yep"))) String) "Stringify did not return a String from byte array.")
  (is (= "Yep" (stringify (bytify "Yep"))) "Conversion of string to bytes and back to string soup-sandwiched."))

(deftest test-byteme
  (is (= 20 (count (byteme "TEST123" 20))) "Did not return the correct number of bytes"))

(deftest test-encryption-decryption
  (let [skey (spec-key "PASSKEY")
        value "Encrypt Me, Monkeyboy!"]
    (is (= value (stringify (decrypt (encrypt value skey) skey))) "Failed to get the same value through the encryption/decryption process")))

(deftest test-encoding?
  (is (encoded-hex? "ENC(ASD)!") "Not detecting hex encoding correctly.")
  (is (not (encoded-hex? "ENC(ASD)")) "Not detecting hex encoding correctly.")
  (is (encoded-base64? "ENC(ASD)") "Not detecting base64 encoding correctly.")
  (is (not (encoded-base64? "ENC(ASD)!")) "Not detecting base64 encoding correctly.")
  (is (encoded? "ENC(ASD)!") "Not detecting encoding correctly.")
  (is (encoded? "ENC(ASD)") "Not detecting encoding correctly.")
  (is (not (encoded? "ENC!(ASD)")) "Not detecting encoding correctly.")
  (is (not (encoded? "LJSDFKLJSDLKJFK")) "Not detecting encoding correctly."))

(deftest test-encoding-decoding
  (let [value "Encode Me, Monkeyboy.  Yippee! Kai! Yay!"
        pass "PASSKEY"
        skey (spec-key pass)
        decr (decrypter pass)]
    (is (= value (decode (encode-base64 value))) "Base64 codec failed on plain string.")
    (is (= value (decode (encode-hex value))) "Hexidecimal codec failed on plain string.")
    (is (= value (decode (encode-base64 (encrypt value skey)) decr)) "Base64 codec failed on encrypted string.")
    (is (= value (decode (encode-hex (encrypt value skey)) decr)) "Hexidecimal codec failed on encrypted string.")))




