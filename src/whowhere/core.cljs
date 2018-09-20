(ns whowhere.core
  (:require [whowhere.entry-points]))

(defn raw-members []
  (->> (.. js/SpreadsheetApp
      getActiveSpreadsheet
      (getSheetByName "members")
      getDataRange
      getValues)
    array-seq
    (map array-seq)))

(defrecord Member [name, location, projects, icon])
(defrecord Project [name, location, members])

(defn to-members [raw-members]
  (->> raw-members
    (map #(Member. (nth % 0) (nth % 1) (js->clj (.split (nth % 2) #",[\s]*")) (nth % 3)))))

(defn project-names [members]
  (->> members
    (reduce (fn [accum, member] (concat accum (:projects member))) [])
    (distinct)
    (sort)))

(defn location-names [members]
  (->> members
    (map #(:location %))
    (distinct)
    (sort)))

(defn detect-members [members project-name]
  (reduce (fn [accum member] (if (some (partial = project-name) (:projects member)) (cons member accum) accum)) [] members))

(defn projects [members]
  (let [project-names (project-names members)] 
    (->> project-names
      (map (fn [project-name] [project-name (detect-members members project-name)]))
      (map (fn [[project-name members]] [project-name (location-names members) members]))
      (map #(apply ->Project %)))))

(defn ^:export start []
  (->> (raw-members)
    (to-members)
    (projects)
    (clj->js)))

(defn ^:export index []
  (.. js/HtmlService
    (createTemplateFromFile "index")
    evaluate))

