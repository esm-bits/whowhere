(ns whowhere.core
  (:require [whowhere.entry-points]))

(defn members []
  (->> (.. js/SpreadsheetApp
      getActiveSheet
      getDataRange
      getValues)
    array-seq
    (map array-seq)))

(defn ^:export start []
  (members))

(defn ^:export index []
  (.. js/HtmlService
    (createTemplateFromFile "index")
    evaluate))

