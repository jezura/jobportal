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
	    datadeleteend();
        alert("Došlo k chybě při požadavku GET na OfferRestController -> /admin/getOffersCount v metodě getOffersCount() skriptu adminOfferDataDelete. Error in adminOfferDataDelete.js");
	});
}

// function show and hide part of form according to selected options in switch controls by user
function showFormPart() {
  var checkBox1 = document.getElementById("switchDeleteAll");
  var checkBox2 = document.getElementById("switchOnlyExpired");
  var dates = document.getElementById("dates");
  var expired = document.getElementById("expired");

  if (checkBox1.checked == true){
      dates.style.display = "none";
      expired.style.display = "none";
    } else if (checkBox2.checked == true){
        dates.style.display = "none";
        expired.style.display = "block";
    } else {
        dates.style.display = "block";
        expired.style.display = "block";
    }
}

// function initiate and fill HTML text elements with data and drive loader animation with timing
// and querying for deleting status info in intervals
function datadeletestart() {
	var loader = document.getElementById("loader");
	var filters = document.getElementById("filters");
	var info = document.getElementById("info");
	loader.innerHTML = "<div class='loader' style='margin:0 auto'></div>";
	filters.innerHTML = "<i style='color:blue' class='fas fa-filter'></i> Mažu všechna data nabídek starších než:<br> ";
	info.innerHTML = "Processing ...";

// loading animation
	var i = 0;
	if (i == 0) {
		i = 1;
		var width = 1;
		var id = setInterval(frame, 1);
		function frame() {
		  if (width >= 50) {
			width=1;
		  }else{
			if(width == 49) {
				if((info.textContent.includes("Mažu")) || (info.textContent.includes("Processing"))) {
					getOffersCount();
					location.href = "#down";
				}else{
					return;
				}
			}
			width++;
		  }
		}
	}
}

// main function - deleting of offers data from app database, it ads the result of process to HTML message element
function deleteOffers() {
    datadeletestart();
    var checkBox1 = document.getElementById("switchDeleteAll");
    var checkBox2 = document.getElementById("switchOnlyExpired");

	var info = document.getElementById("info");
	var message = document.getElementById("message_notification");
	var filters = document.getElementById("filters");

    if (checkBox1.checked == true){
        info.innerHTML = "Mažu data všech pracovních nabídek...";
        filters.innerHTML = "";
        url = "deleteAllOffers";
        $.get(url, function(response) {
            location.href = "#top";
            if(response > 0){
                message.innerHTML += "<div class='alert alert-success alert-dismissible fade show'><button type='button' class='close' data-dismiss='alert'>&times;</button><i class='fas fa-check-circle' style='color:green'></i><strong>  Data všech pracovních nabídek byla úspěšně odstraněna (celkem " + response + " nabídek smazáno)</strong></div>";
            }else{
                message.innerHTML += "<div class='alert alert-warning alert-dismissible fade show'><button type='button' class='close' data-dismiss='alert'>&times;</button><i class='fas fa-exclamation-triangle' style='color:orange'></i><strong>  Nebyla nalezena žádná data k odstranění</strong></div>";
            }
            datadeleteend();
        }).fail(function() {
            datadeleteend();
            alert("Došlo k chybě při požadavku GET na OfferRestController -> /admin/deleteAllOffers v metodě deleteOffers() skriptu adminOfferDataDelete. Error in adminOfferDataDelete.js");
        });
        }
    else if (checkBox2.checked == true){
        info.innerHTML = "Mažu všechna data expirovaných nabídek...";
        url = "deleteAllExpiredOffers";
        $.get(url, function(response) {
            location.href = "#top";
            if(response > 0){
               message.innerHTML += "<div class='alert alert-success alert-dismissible fade show'><button type='button' class='close' data-dismiss='alert'>&times;</button><i class='fas fa-check-circle' style='color:green'></i><strong>  Data všech expirovaných pracovních nabídek byla úspěšně odstraněna (celkem " + response + " nabídek smazáno)</strong></div>";
            }else{
               message.innerHTML += "<div class='alert alert-warning alert-dismissible fade show'><button type='button' class='close' data-dismiss='alert'>&times;</button><i class='fas fa-exclamation-triangle' style='color:orange'></i><strong>  Nebyla nalezena žádná data k odstranění</strong></div>";
            }
            datadeleteend();
        }).fail(function() {
            datadeleteend();
            alert("Došlo k chybě při požadavku GET na OfferRestController -> /admin/deleteAllExpiredOffers v metodě deleteOffers() skriptu adminOfferDataDelete. Error in adminOfferDataDelete.js");
        });
    }else{
        var oldestInsertionDate = document.forms["deleteForm"]["oldestInsertionDate"].value.toString();
        var oldestEditDate = document.forms["deleteForm"]["oldestEditDate"].value.toString();

        if((oldestInsertionDate.length == 0) && (oldestEditDate.length == 0)) {
            datadeleteend();
            alert("Pokud se pokoušíte odstranit nabídky podle data, musíte alespoň jedno validní datum uvést...");
            return;
        }


	    info.innerHTML = "Mažu zvolená data...";
        var filters = document.getElementById("filters");
        if(oldestInsertionDate.length > 0) {
            filters.innerHTML += "datum vložení : " + oldestInsertionDate + ", ";
        }
        if(oldestEditDate.length > 0) {
            filters.innerHTML += "datum editace: " + oldestEditDate + ", ";
        }

        url = "deleteAllOffersBeforeGivenDates";
        params = {oldestInsertionDate: oldestInsertionDate, oldestEditDate: oldestEditDate};

        $.get(url, params, function(response) {
            location.href = "#top";
            if(response > 0){
               message.innerHTML += "<div class='alert alert-success alert-dismissible fade show'><button type='button' class='close' data-dismiss='alert'>&times;</button><i class='fas fa-check-circle' style='color:green'></i><strong>  Data všech odpovídajících pracovních nabídek byla úspěšně odstraněna (celkem " + response + " nabídek smazáno)</strong></div>";
            }else{
               message.innerHTML += "<div class='alert alert-warning alert-dismissible fade show'><button type='button' class='close' data-dismiss='alert'>&times;</button><i class='fas fa-exclamation-triangle' style='color:orange'></i><strong>  Nebyla nalezena žádná data k odstranění</strong></div>";
            }
            datadeleteend();
        }).fail(function() {
            datadeleteend();
            alert("Došlo k chybě při požadavku GET na OfferRestController -> /admin/deleteAllOffersBeforeGivenDates v metodě deleteOffers() skriptu adminOfferDataDelete. Error in adminOfferDataDelete.js");
        });
    }
}

// function clear all info/status HTML elements after loading process finished
function datadeleteend() {
	var loader = document.getElementById("loader");
	var info = document.getElementById("info");
	var filters = document.getElementById("filters");
	loader.innerHTML = "";
	info.innerHTML = "";
	filters.innerHTML = "";
	getOffersCount();
}