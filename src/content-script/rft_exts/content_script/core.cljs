(ns rft-exts.content-script.core
  (:require [cljs.core.async :refer [go-loop go <! chan]]
            [chromex.logging :refer-macros [log]]
            [chromex.ext.storage :as storage]
            [rft-exts.shared.storage :as s]
            [chromex.chrome-event-channel :refer [make-chrome-event-channel]]))

(defn get-hostname [] (.. js/window -location -hostname))

(defn update-page [width]
  (set! (.. js/document -body -style -margin) "auto")
  (set! (.. js/document -body -style -width) (str width "%")))

(defn restore-width! []
  (go
    (when-let [width (<! (s/retrieve-data (get-hostname)))]
      (update-page width))))

(defn handle-storage-changes [changes]
  (when-let [width (get-in (js->clj changes) [(get-hostname) "newValue"])]
    (update-page width)))

(defn process-chrome-event [_ event]
  (let [[event-id event-args] event]
    (case event-id
      ::storage/on-changed (apply handle-storage-changes event-args)
      nil)))

(defn run-chrome-event-loop! [chrome-event-channel]
  (go-loop [event-num 1]
    (when-some [event (<! chrome-event-channel)]
      (process-chrome-event event-num event)
      (recur (inc event-num)))))

(defn boot-chrome-event-loop! []
  (let [chrome-event-channel (make-chrome-event-channel (chan))]
    (storage/tap-all-events chrome-event-channel)
    (run-chrome-event-loop! chrome-event-channel)))

(defn init! []
  (restore-width!)
  (boot-chrome-event-loop!))
