(ns rft-exts.popup.components)

(defn label []
  [:div.label "Slide to adjust the width of the page"])

(defn slider [value update]
  [:div.slider-container
   [:span "0%"]
   [:input {:type "range" :min 0 :max 100 :value value
            :on-change (fn [e] (update (js/parseInt (.. e -target -value))))}]
   [:span "100%"]])

(defn button [on-click-handler]
  [:div.button-container
   [:input {:type "button" :value "Confirm"
            :on-click #(on-click-handler)}]])