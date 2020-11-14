(ns rft-exts.content-script
  (:require [rft-exts.content-script.core :as core]
            [chromex.support :refer [runonce]]))

(runonce
  (core/init!))
