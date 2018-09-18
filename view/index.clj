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
  ))

(def index-html
  (html5
    (html
      [:head
        [:script {:src "https://cdn.jsdelivr.net/npm/vue/dist/vue.js"}]
        [:base {:target "_top"}]]
        [:style styles]
      [:body
        [:div {:id "projects"} 
          [:div {:v-for "project in projects" :class "project"}
            [:p "{{ project['name'] }}"]
            [:ul 
              [:li {:v-for "member in project['members']" :class "member"}
                [:p "{{ member['name'] }}"]
                [:img {"v-bind:src" "member['icon']" :class "icon"}
                [:p "{{ member['location'] }}"]]]]]]
        [:script "
          google.script.run.withSuccessHandler(initializeVue).calc();
          function initializeVue(projects) {
            new Vue({
              el: '#projects',
              data: {
                projects: projects
              }})
            } 
        "]])))

(println index-html)
