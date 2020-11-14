(ns rft-exts.shared.storage
  (:require [chromex.ext.storage :as storage]
            [cljs.core.async :refer [go <!]]
            [chromex.protocols.chrome-storage-area :as sa]))

(defn retrieve-data [key]
   (let [storage (storage/get-local)]
    (go
      (let [[[obj] error] (<! (sa/get storage key))]
        (if error
          (error "error retrieving rft-data from storage:" error)
          (when-let [stored-data (get (js->clj obj) key)]
            stored-data))))))

(defn persist-data! [key data]
  (let [storage (storage/get-local)]
    (sa/set storage (clj->js {key data}))))

(defn clear-data! []
  (let [storage (storage/get-local)]
    (sa/clear storage)))