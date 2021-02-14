getOffersCount();

// function get via GET method actual count of job offers stored in app database
// it adds text result information to the HTML element
function getOffersCount() {
url = "/admin/getOffersCount";

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
