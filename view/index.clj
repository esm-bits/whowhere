(use '[leiningen.exec :only (deps)])
(deps '[[hiccup "1.0.5"]
        [garden "1.3.6"]])

(ns whowhere.core)
(use 'hiccup.core 'hiccup.page)
(use 'garden.core)

(def styles 
  (css
    [:.project {
      :border "solid"
      :border-width "1px"
      :margin "1px 2px"
      :border-radius "5px 40px"
    }]))

(def index-html
  (html5
    (html
      [:head
        [:script {:src "https://cdn.jsdelivr.net/npm/vue/dist/vue.js"}]
        [:base {:target "_top"}]]
        [:style styles]
      [:body
        [:ul {:id "projects"} 
          [:li {:v-for "project in projects" :class "project"}
            [:p "{{ project['name'] }}"]
            [:ul 
              [:li {:v-for "member in project['members']"}
                "{{ member }}"]]]]
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
