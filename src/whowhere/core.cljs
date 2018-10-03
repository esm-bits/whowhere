(ns whowhere.core
  (:require [whowhere.entry-points]))

(defn raw-members []
  (->> (.. js/SpreadsheetApp
      getActiveSpreadsheet
      (getSheetByName "members")
      getDataRange
      getValues)
    array-seq
    (map array-seq)
    (rest)))

(def settings
  (->> (.. js/SpreadsheetApp
      getActiveSpreadsheet
      (getSheetByName "settings")
      getDataRange
      getValues)
    array-seq
    (map array-seq)
    (rest)
    (reduce (fn [accum [key val]] (assoc accum key val)) {})))

(defrecord Member [id, name, real-name, location, projects, icon])
(defrecord Project [name, location, members])

(defn to-members [raw-members]
  (->> raw-members
    (map #(Member. (nth % 0) (nth % 1) (nth % 2) (nth % 3) (js->clj (.split (nth % 4) #",[\s]*")) (nth % 5)))))

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

(def project-list
  (->> (raw-members)
    (to-members)
    (projects)))

(defn ^:export start []
  (clj->js
    {
      "projects" project-list
      "settings" (clj->js settings)
    }))

(defn ^:export index []
  (.. js/HtmlService
    (createTemplateFromFile "index")
    evaluate))

