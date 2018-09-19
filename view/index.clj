(use '[leiningen.exec :only (deps)])
(deps '[[hiccup "1.0.5"]
        [garden "1.3.6"]])

(ns whowhere.core)
(use 'hiccup.core 'hiccup.page)
(use 'garden.core)

(def styles 
  (css
    [:p {
      :margin "1px"
    }]
    [:ul {
      :padding "0px"
    }]
    [:li {
      :list-style "none"
    }]
    [:.project {
      :border "solid"
      :border-color "#999"
      :border-radius "40px"
      :border-width "1px"
      :float "left"
      :height "180px"
      :margin "1px 2px"
      :padding "0px 15px"
      :text-align "center"
      :width "fit-content"
      :background-color "#def"
    }]
    [:.member {
      :font-size "12px"
      :border "solid"
      :border-width "1px"
      :border-color "#999"
      :margin "1px 2px"
      :border-radius "5px"
      :float "left"
      :width "100px"
    }]
    [:.icon {
      :width "50px"
      :height "50px"
    }]
    [:.unvisible {
      :display "none"
    }]
    [:.force_visible {
      :display "block !important"
    }]
  ))

(def index-html
  (html5
    (html
      [:head
       [:script {:defer nil :src "https://use.fontawesome.com/releases/v5.3.1/js/all.js" :integrity "sha384-kW+oWsYx3YpxvjtZjFXqazFpA7UP/MbiY4jvs+RWZo2+N94PFZ36T6TFkc9O3qoB" :crossorigin "anonymous"}]
        [:script {:src "https://cdn.jsdelivr.net/npm/vue/dist/vue.js"}]
        [:base {:target "_top"}]]
        [:style styles]
      [:body
        [:div {:id "visible"}
          [:i {:class "fa fa-spinner fa-pulse fa-3x fa-fw" "v-bind:class" "{unvisible: active}"}]
          [:div {:class "unvisible" "v-bind:class" "{force_visible: active}"} 
            [:div {:id "projects"}
              [:div {:v-for "project in projects" :class "project"}
                [:p "{{ project['name'] }}"]
                [:ul
                  [:li {:v-for "member in project['members']" :class "member"}
                    [:p "{{ member['name'] }}"]
                    [:img {"v-bind:src" "member['icon']" :class "icon"}
                    [:p "{{ member['location'] }}"]]]]]]]]
        [:script "
          google.script.run.withSuccessHandler(initializeVue).calc();
          function initializeVue(projects) {
            new Vue({
              el: '#projects',
              data: {
                projects: projects
              }})
            new Vue({
              el: '#visible',
              data: {
                active: true
              }})
            } 
        "]])))

(println index-html)
