(ns rft-exts.popup
  (:require [rft-exts.popup.core :as core]
            [chromex.support :refer [runonce]]))

(runonce
  (core/init!))
