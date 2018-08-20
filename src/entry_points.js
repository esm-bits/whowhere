function doGet() {
  // whowhere.core.start();
  return HtmlService.createTemplateFromFile("hello").evaluate();
}

function calc() {
  return whowhere.core.start();
}

