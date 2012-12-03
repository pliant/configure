# About

Provides a small Clojure library that adds encryptable configuration to your software application or software product.

## Adding Configuration To Your Application

You add configuration to your application by:

1. Creating a configuration namespace/clj file in your application.
2. Define a VAR to hold the configuration properties acquired using the handy dandy slurp-config function.
3. Reference the configuration namespace/VAR from where ever you need the configuration values.

Below is an example:

```clojure
(ns myapplication.config
  (:use 'pliant.configure.props )
  (:use 'pliant.configure.sniff ))

(def config
  (slurp-config "myapplication.props"
    (sniff "SECRETKEY"))

(println (:my.property config))
```
The VAR "config" now contains a map with all of the key/value pairs from the "myapplication.props" properties file.  The "keys" have been transformed to keywords, and the values have been properly decrypted if needed. The code can be broken down as follows:

* _pliant.configure.props_ is a namespace that provides functions for loading/decrypting properties files.
* _pliant.configure.sniff_ is a namespace that provides a way to find/sniff out a value based on context.
* _slurp-config_ is a function that will load a properties file from the classpath, load it into a map, and decrypt any values that are tagged as encrypted. The function is overloaded, with the following signatures:
    + [NameOfPropertyFile] - loads the property file and no decryption is attempted.
    + [NameOfPropertyFile PassKey] - loads the property file and decrypts using the PassKey and the default encryption configuration (AES, 128-bit)
    + NameOfPropertyFile PassKey Options] - loads the property file and decrypts using the PassKey and the decryption configuration (changing algorithm, size)
* _sniff_ is a function that takes a word and tries to find a value based on that word. It will look in the following locations for that value:
    + The value of an environment variable matching the word.
    + The value of a system property matching the word.
    + The contents of a file on the classpath with a name matching the word.
    + The value of a naming context based on the word.
    + The value of a naming context based on "java:/comp/env/" + word.
    + If the value has not been found, nil is returned.

## Creating Encrypted Configuration Values

All private data, such as credentials to connect to external applications (services, database) should be externalized and encrypted. So how do you get an encrypted value? Easy-peasy, it is. All you need to do is open a REPL, import the pliant.configure.codec namespace, and encrypt a value using the provided functions:

```clojure
-> (use 'pliant.configure.codec)
nil
-> (encode-hex (encrypt "This Is My Value" (spec-key "This Is My PassKey")))
"ENC(78c5e513dc22a92f92f200a9e51ffd26ab7007fbf166d22d665bdd43f8baeffc)!"
-> (encode-base64 (encrypt "This Is My Value" (spec-key "This Is My PassKey")))
"ENC(eMXlE9wiqS+S8gCp5R/9JqtwB/vxZtItZlvdQ/i67/w=)"
```

The values "ENC(78c5e513dc22a92f92f200a9e51ffd26ab7007fbf166d22d665bdd43f8baeffc)!" and "ENC(eMXlE9wiqS+S8gCp5R/9JqtwB/vxZtItZlvdQ/i67/w=)" is what you would use as the value in your properties file.

    my-hex-key=ENC(78c5e513dc22a92f92f200a9e51ffd26ab7007fbf166d22d665bdd43f8baeffc)!    
    my-base64-key=ENC(eMXlE9wiqS+S8gCp5R/9JqtwB/vxZtItZlvdQ/i67/w=)

Encoding the encrypted value in either hexadecimal or base64 format is your choice, as the decode function will detect which you have used and decode/decrypt appropriately. You only need to make sure that the value for the passkey is the value that is passed to the slurp-config method(the value that is found by sniffing)

## Why Sniff?

Sniffing for a private passkey rather than coding the location where it is found allows for the flexibility of choosing where we set that value base on what is most secure for the environment. For some installations it may be best to set it as a context in the application server, where others it should be supplied through environmental variables. It in the end, it gives us choice and flexibility while not exposing in the code exactly where to find the passkey.
