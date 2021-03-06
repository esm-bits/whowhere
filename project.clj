(defproject whowhere "0.1.0-SNAPSHOT"

  :dependencies [
    [org.clojure/clojure "1.9.0-alpha13"]
    [org.clojure/clojurescript "1.9.229"]
    [hiccup "1.0.5"]
    [garden "1.3.6"]]

  :plugins [
    [lein-cljsbuild "1.1.4"]
    [lein-exec "0.3.1"]]

  :clean-targets ^{:protect false} [:target-path :compile-path "target" "dist"]

  :cljsbuild {:builds
    {:main {:source-paths ["src"]
      :compiler {:main whowhere.core
        :optimizations :advanced
        :output-to "dist/Code.gs"
        :output-dir "target"
        :pretty-print false
        :externs ["resources/gas.ext.js"]
        :foreign-libs [{:file "src/entry_points.js"
          :provides ["whowhere.entry-points"]}]}}}})
