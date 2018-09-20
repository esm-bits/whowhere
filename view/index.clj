(use '[leiningen.exec :only (deps)])
(deps '[[hiccup "1.0.5"]
        [garden "1.3.6"]])

(ns whowhere.core)
(use 'hiccup.core 'hiccup.page)
(use 'garden.core)

(def styles 
  (css
    [:body {
      :font-family "Arial, Verdana, sans-serif"
      :color "#666"
    }]
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
      :border-color "#999 !important"
      :border-radius "40px"
      :border-width "4px !important"
      :float "left"
      :min-height "120px"
      :margin "1px 2px"
      :padding "10px 15px"
      :text-align "center"
      :width "fit-content"
    }]
    [:.member {
      :font-size "12px"
      :margin "1px 2px"
      :border-radius "5px"
      :float "left"
      :min-width "100px"
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
    [:.loading {
      :color "#aaa"
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
        [:h1 "ITSだれどこ"]
        [:div {:id "visible"}
          [:i {:class "loading fa fa-spinner fa-pulse fa-3x fa-fw" "v-bind:class" "{unvisible: active}"}]
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
