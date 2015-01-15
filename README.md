# clj-beautify

`clj-beautify` is a clojure formatter implemented in clojure.  The formatting
specification is based on the `clojure.pprint/write` function.

## Installation

As a dependency in a clojure project:

```clojure
[clj-beautify "0.1.0"]
```

As a standalone cli tool

```sh
git clone https://github.com/comamitc/clj-beautify.git
cd clj-beautify
lein uberjar
```

## Usage

As a standalone cli tool:

```sh
./clj-beautify.sh clj ./path/or/file/to/scan
```

As a library:

```clojure
(:require [clj-beautify.core :refer [format-file]])
(format-file "./path/or/file/to/scan" "clj") ;; can be "clj" or "edn"
```

## License

Copyright © 2015 Mitch Comardo

The MIT License (MIT)

