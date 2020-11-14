(ns rft-exts.popup.core
  (:require [chromex.logging :refer-macros [log]]
            [reagent.core :as r]
            [cljs.core.async :refer [go <!]]
            [reagent.dom :as rdom]
            [chromex.ext.tabs :as t]
            [rft-exts.popup.components :as c]
            [rft-exts.shared.storage :as s]
            [rft-exts.shared.url :as u]))

(def data (r/atom 100))

(defn init-data! [hostname]
  (go
    (when-let [rft-data (<! (s/retrieve-data hostname))]
      (reset! data rft-data))))

(defn clear []
  (go
    (s/clear-data!)
    (reset! data 100)
    (t/reload)))

(defn app [hostname]
  [:div#main
   [c/label]
   [c/slider @data #(reset! data %)]
   [c/confirm-button #(s/persist-data! hostname @data)]
   [c/reset-button clear]
   [c/info]])

(defn mountit [hostname]
  (rdom/render [app hostname]
               (.getElementById js/document "app")))

(defn init! []
  (go
    (let [hostname (<! (u/get-hostname-from-popup))]
      (log "POPUP: init")
      (init-data! hostname)
      (mountit hostname))))
