checkCodebookDataPresent();
getOffersCount();

// function checks via GET method if all codebook data tables are populated with data
// it adds text result information to the HTML element
function checkCodebookDataPresent() {
url = "checkCodebookData";

$.get(url, function(response) {
    var codebookResult = document.getElementById("codebookCheckResult");
    if(response == true) {
        codebookResult.innerHTML = "<i style='color:green' class='fas fa-check-circle'></i></i> Všechny číselníkové tabulky jsou naplněné daty...";
    }else{
        codebookResult.innerHTML = "<i style='color:red' class='fas fa-exclamation-triangle'></i> Minimálně jedna číselníková tabulka je zatím prázdná...";
    }
}).fail(function() {
    alert("Došlo k chybě při požadavku GET na CodebookRestController -> /admin/checkCodebookData v metodě checkCodebookDataPresent() skriptu adminOverview. Error in adminOverview.js");
});
}


// function get via GET method actual count of job offers stored in app database
// it adds text result information to the HTML element
function getOffersCount() {
url = "getOffersCount";

$.get(url, function(response) {
    var offersResult = document.getElementById("offersCount");
    if(response > 0) {
        offersResult.innerHTML = "<i style='color:green' class='fas fa-check-circle'></i> Databáze obsahuje data celkem <b>" + response + "</b> nahraných externích pracovních nabídek...";
    }else{
        offersResult.innerHTML = "<i style='color:red' class='fas fa-exclamation-triangle'></i> Databáze zatím neobsahuje žádné externí pracovní nabídky...";
    }
}).fail(function() {
    alert("Došlo k chybě při požadavku GET na OfferRestController -> /admin/getOffersCount v metodě getOffersCount() skriptu adminOverview. Error in adminOverview.js");
});
}
