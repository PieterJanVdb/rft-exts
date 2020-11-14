(ns rft-exts.shared.url
  (:require [chromex.ext.tabs :as t]
            [cljs.core.async :refer [go <!]]))

(defn get-hostname-from-popup []
  (go
    (let [[[tab] error] (<! (t/query #js {"active" true}))]
      (if error
        (error "Error retrieving hostname from popup:" error)
        (when-let [url-str (get (js->clj tab) "url")]
          (when-let [url (js/URL. url-str)]
            (.-hostname url)))))))