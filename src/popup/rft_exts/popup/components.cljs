(ns rft-exts.popup.components)

(defn label []
  [:div.label "Slide to adjust the width of the page"])

(defn slider [value update]
  [:div.slider-container
   [:span "0%"]
   [:input {:type "range" :min 0 :max 100 :value value
            :on-change (fn [e] (update (js/parseInt (.. e -target -value))))}]
   [:span "100%"]])

(defn info []
  [:div.info "(After clearing a refresh might be needed for other open tabs modified by this extension)"])

(defn confirm-button [on-click-handler]
  [:div.button-container.confirm
   [:input {:type "button" :value "Confirm"
            :on-click #(on-click-handler)}]])

(defn reset-button [on-click-handler]
  [:div.button-container.reset
   [:input {:type "button" :value "Clear for all pages"
            :on-click #(on-click-handler)}]])