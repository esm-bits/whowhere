(use '[leiningen.exec :only (deps)])
(deps '[[hiccup "1.0.5"]])

(ns whowhere.core)
(use 'hiccup.core 'hiccup.page)

(def index-html
  (html5
    (html
      [:head
        [:script {:src "https://cdn.jsdelivr.net/npm/vue/dist/vue.js"}]]
        [:base {:target "_top"}]
      [:body
        [:div {:id "app"} "{{ message }}"]
        [:script "
          var app = new Vue({
            el: '#app',
            data: {
              message: <?= calc(); ?>
            }})
          "]])))

(println index-html)
