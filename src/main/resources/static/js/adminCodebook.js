checkCodebookDataPresent();

// function checks via GET method if all codebook data tables are populated with data
// it adds text result information to the HTML element and dynamically change load button text
function checkCodebookDataPresent() {
url = "checkCodebookData";

$.get(url, function(response) {
    var result = document.getElementById("codebookCheckResult");
    var button = document.getElementById("buttonLoad");
    if(response == true) {
        result.innerHTML = "<i style='color:green' class='fas fa-check-circle'></i></i> Všechny číselníkové tabulky jsou naplněné daty...";
        button.innerHTML = "Přepsat/aktualizovat data všech číselníků";
    }else{
        result.innerHTML = "<i style='color:red' class='fas fa-exclamation-triangle'></i> Minimálně jedna číselníková tabulka je zatím prázdná...";
        button.innerHTML = "Nahrát data všech číselníků";
    }
}).fail(function() {
    dataloadingend();
    alert("Došlo k chybě při požadavku GET na CodebookRestController -> /admin/checkCodebookData v metodě checkCodebookDataPresent() skriptu adminCodebook. Error in adminCodebook.js");
});
}

// function initiate and fill HTML text elements with data and drive loader animation with timing
// and querying for loading status info in intervals
function dataloadingstart() {
var loader = document.getElementById("loader");
var info = document.getElementById("info");
var progress = document.getElementById("progress");
var count = document.getElementById("count");
var time = document.getElementById("time");
time.innerHTML = "<i class='fas fa-clock' style='color:blue'></i> Doba trvání přibližně 6 minut (v závislosti na výkonu)...";
loader.innerHTML = "<div class='loader' style='margin:0 auto'></div>";
info.innerHTML = "Nahrávání dat...";

// loading animation
var i = 0;
  if (i == 0) {
    i = 1;
    var width = 1;
    var id = setInterval(frame, 1);
    function frame() {
      if (width >= 50) {
        width=1;
      } else {
      	if(width == 49) {
      	    var i = document.getElementById("info").textContent;
      	    if(i.includes("Nahrávání")) {
      	        getCodebookParsingProgress();
                getCodebookParsedRecords();
      	    }else{
      	        return;
      	    }
        }
        width++;
      }
    }
  }
}

// function clear all info/status HTML elements after loading process finished
function dataloadingend() {
var loader = document.getElementById("loader");
var info = document.getElementById("info");
var progress = document.getElementById("progress");
var count = document.getElementById("count");
var time = document.getElementById("time");
loader.innerHTML = "";
info.innerHTML = "";
progress.innerHTML = "";
count.innerHTML = "";
time.innerHTML = "";
}

// main function - parsing and loading of codebook data to app database, it ads the result of process to HTML message element
function parseAllCodebookData() {
dataloadingstart();
url = "parseAllCodeBookData";

$.get(url, function(response) {
    var message = document.getElementById("message_notification");
    if(response == true) {
        message.innerHTML += "<div class='alert alert-success alert-dismissible fade show'><button type='button' class='close' data-dismiss='alert'>&times;</button><i class='fas fa-check-circle' style='color:green'></i><strong>  Data číselníků byla úspěšně uložena do databáze</strong></div>";
    }else{
        message.innerHTML += "<div class='alert alert-danger alert-dismissible fade show'><button type='button' class='close' data-dismiss='alert'>&times;</button><i class='fas fa-exclamation-triangle' style='color:red'></i><strong>  Nahrávání dat se nezdařilo</strong></div>";
    }
    dataloadingend();
    checkCodebookDataPresent();

}).fail(function() {
    dataloadingend()
    alert("Došlo k chybě při požadavku GET na CodebookRestController -> /admin/parseAllCodeBookData v metodě parseAllCodebookData() skriptu adminCodebook. Error in adminCodebook.js");
});
}

// function gets codebook parsing progress from REST controller in intervals by loader and displays this info in text HTML element
function getCodebookParsingProgress() {
 url = "codebookParsingProgress";

 $.get(url, function(response) {
     progress.innerHTML = "Zpracováno " + response + " ze 14 číselníkových tabulek...";
 }).fail(function() {
    dataloadingend()
    alert("Došlo k chybě při požadavku GET na CodebookRestController -> /admin/codebookParsingProgress v metodě getCodebookParsingProgress() skriptu adminCodebook. Error in adminCodebook.js");
 });
 }

// function gets number of parsed records from REST controller in intervals by loader and displays this info in text HTML element
 function getCodebookParsedRecords() {
 url = "codebookParsedRecords";

 $.get(url, function(response) {
     count.innerHTML = "Ukládám <b style='color: blue'>" + response +"</b>. datový záznam...";

 }).fail(function() {
    dataloadingend()
    alert("Došlo k chybě při požadavku GET na CodebookRestController -> /admin/codebookParsedRecords v metodě getCodebookParsedRecords() skriptu adminCodebook. Error in adminCodebook.js");
 });
 }