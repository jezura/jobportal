var totalOffersCount = 0;
getOffersCount();

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
	    dataloadingend();
        alert("Došlo k chybě při požadavku GET na OfferRestController -> /admin/getOffersCount v metodě getOffersCount() skriptu adminOfferDataLoad. Error in adminOfferDataLoad.js");
	});
}

// function initiate and fill HTML text elements with data and drive loader animation with timing
// and querying for loading status info in intervals
function dataloadingstart(useApiSource) {
	var loader = document.getElementById("loader");
	var info = document.getElementById("info");
	var actualInsertionDate = document.getElementById("actualInsertionDate");
	var actualEditDate = document.getElementById("actualEditDate");
	var count = document.getElementById("count");
	var filters = document.getElementById("filters");
	loader.innerHTML = "<div class='loader' style='margin:0 auto'></div>";
	filters.innerHTML = "<i style='color:blue' class='fas fa-filter'></i> Nahrávám nabídky splňující podmínky:<br> ";
	info.innerHTML = "Čekání na data...";
	location.href = "#down";

// loading animation
	var i = 0;
	if (i == 0) {
		i = 1;
		var width = 1;
		var id = setInterval(frame, 1);
		function frame() {
		  if (width >= 100) {
			width=1;
		  }else{
			if(width == 99) {
				var i = document.getElementById("info").textContent;
				if((i.includes("Nahrávání")) || (i.includes("Čekání"))) {
					getParsedOffersCount(useApiSource);
					getActualOfferInsertionDate();
					getActualOfferEditDate();
					getOffersCount();
				}else{
					return;
				}
			}
			width++;
		  }
		}
	}
}

// main function - parsing and loading of offers data to app database, it ads the result of process to HTML message element
function parseOffers() {
    var insertionDateFrom = document.forms["dateForm"]["insertionDateFrom"].value.toString();
    var insertionDateTo = document.forms["dateForm"]["insertionDateTo"].value.toString();
    var editDateFrom = document.forms["dateForm"]["editDateFrom"].value.toString();
    var editDateTo = document.forms["dateForm"]["editDateTo"].value.toString();
    var chcbox1 = document.getElementById("skipEmptyDescription");
    var chcbox2 = document.getElementById("useApiSource");
    var skipEmptyDescription = "switchedOff";
    var useApiSource = "switchedOff";
	
    if(chcbox1.checked == true) {
        skipEmptyDescription = "switchedOn";
    }
    if(chcbox2.checked == true) {
        useApiSource = "switchedOn";
    }

    dataloadingstart(useApiSource);
    var filters = document.getElementById("filters");
    if(insertionDateFrom.length > 0) {
        filters.innerHTML += "datum vložení od: " + insertionDateFrom + ", ";
    }
    if(insertionDateTo.length > 0) {
        filters.innerHTML += "datum vložení do: " + insertionDateTo + ", ";
    }
    if(editDateFrom.length > 0) {
        filters.innerHTML += "<br>datum poslední změny od: " + editDateFrom + ", ";
    }
    if(editDateTo.length > 0) {
        filters.innerHTML += "datum poslední změny do: " + editDateTo + ", ";
    }

    url = "parseOffers";
    params = {insertionDateFrom: insertionDateFrom, insertionDateTo: insertionDateTo,
    editDateFrom: editDateFrom, editDateTo: editDateTo, skipEmptyDescription: skipEmptyDescription,
    useApiSource: useApiSource};

    $.get(url, params, function(response) {
        dataloadingend();
        var message = document.getElementById("message_notification");
        location.href = "#top";
			if(response == true) {
				if(totalOffersCount > 1) {
					message.innerHTML += "<div class='alert alert-success alert-dismissible fade show'><button type='button' class='close' data-dismiss='alert'>&times;</button><i class='fas fa-check-circle' style='color:green'></i><strong>  Zvolená data pracovních nabídek byla úspěšně uložena do databáze. Celkem " + totalOffersCount + " položek uloženo/aktualizováno</strong></div>";
				}else{
					message.innerHTML += "<div class='alert alert-warning alert-dismissible fade show'><button type='button' class='close' data-dismiss='alert'>&times;</button><i class='fas fa-exclamation-triangle' style='color:orange'></i><strong>  Pro definované parametry filtrů nebyly ve zdrojových datech nalezeny žádné odpovídající nabídky k uložení. Zkuste změnit filtry nebo aktualizovat zdroj dat</strong></div>";
				}
			}else{
				message.innerHTML += "<div class='alert alert-danger alert-dismissible fade show'><button type='button' class='close' data-dismiss='alert'>&times;</button><i class='fas fa-exclamation-triangle' style='color:red'></i><strong>  Nahrávání dat se nezdařilo</strong></div>";
			}
			totalOffersCount = 0;
    }).fail(function() {
        dataloadingend();
        alert("Došlo k chybě při požadavku GET na ParsingRestController -> /admin/parseOffers v metodě parseOffers() skriptu adminOfferDataLoad. Error in adminOfferDataLoad.js");
    });
}

// function gets job offers data parsing progress from REST controller in intervals by loader and displays this info in text HTML element
function getParsedOffersCount(useApiSource) {
	url = "parsedOffersCount";

	$.get(url, function(response) {
		totalOffersCount = response;
		if(response <= 1) {
			if(useApiSource.includes("switchedOn")){
				info.innerHTML = "Čekání na: <br><i style='font-size:0.8em; color:red'>https://data.mpsv.cz/od/soubory/volna-mista/volna-mista.json</i>...";
			}else{
				info.innerHTML = "Čekání na data...";
			}
		}else{
			info.innerHTML = "Nahrávání dat...";
			count.innerHTML = "Ukládám data <b style='color:blue'>" + response + "</b>. pracovní nabídky...";
		}
		return response;
	}).fail(function() {
	    dataloadingend();
        alert("Došlo k chybě při požadavku GET na ParsingRestController -> /admin/parsedOffersCount v metodě getParsedOffersCount(useApiSource) skriptu adminOfferDataLoad. Error in adminOfferDataLoad.js");
	});
}

// function get and display info about insertion date of actually parsed job offer in intervals from loader
function getActualOfferInsertionDate() {
	url = "actualParsedOfferInsertionDate";

	$.get(url, function(response) {
		actualInsertionDate.innerHTML = "Datum vložení aktuálně zpracovávané nabídky: <b style='color:blue'>" + response + "</b>.";
	}).fail(function() {
	    dataloadingend();
        alert("Došlo k chybě při požadavku GET na ParsingRestController -> /admin/actualParsedOfferInsertionDate v metodě getActualOfferInsertionDate() skriptu adminOfferDataLoad. Error in adminOfferDataLoad.js");
	});
}

// function get and display info about edit date of actually parsed job offer in intervals from loader
function getActualOfferEditDate() {
	url = "actualParsedOfferEditDate";

	$.get(url, function(response) {
		actualEditDate.innerHTML = "Datum poslední změny aktuálně zpracovávané nabídky: <b style='color:blue'>" + response + "</b>.";
    }).fail(function() {
        dataloadingend();
        alert("Došlo k chybě při požadavku GET na ParsingRestController -> /admin/actualParsedOfferEditDate v metodě getActualOfferEditDate() skriptu adminOfferDataLoad. Error in adminOfferDataLoad.js");
	});
}

// function clear all info/status HTML elements after loading process finished
function dataloadingend() {
	var loader = document.getElementById("loader");
	var info = document.getElementById("info");
	var count = document.getElementById("count");
	var actualInsertionDate = document.getElementById("actualInsertionDate");
	var actualEditDate = document.getElementById("actualEditDate");
	var filters = document.getElementById("filters");
	loader.innerHTML = "";
	info.innerHTML = "";
	count.innerHTML = "";
	actualInsertionDate.innerHTML = "";
	actualEditDate.innerHTML = "";
	filters.innerHTML = "";
}