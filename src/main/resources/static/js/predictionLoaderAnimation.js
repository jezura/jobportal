function loading() {
var buttonToHide = document.getElementById("buttonPersonalizationStart");
var loader = document.getElementById("loader");
var info = document.getElementById("info");
buttonToHide.style.display = "none";
loader.innerHTML = "<div class='loader' style='margin:0 auto'></div>";
var i = 0;
  if (i == 0) {
    i = 1;
    var width = 1;
    var id = setInterval(frame, 1);
    function frame() {
      if (width >= 2000) {
        clearInterval(id);
        i = 0;
      } else {
      	if(width == 30) {
            info.innerHTML += "<br>CV processing...";
        }else if(width == 90) {
            info.innerHTML += "<br>Information extraction...";
        }else if(width == 200) {
            info.innerHTML += "<br>Predicting relevances...";
        }else if(width == 350) {
            info.innerHTML += "<br>Job offers sorting...";
        }else if(width == 500) {
            info.innerHTML += "<br>Almost done...";
        }
        width++;
      }
    }
  }
}

function registrationLoading() {
var buttonToHide = document.getElementById("buttonRegistrationPersonalizationStart");
var loader = document.getElementById("registrationLoader");
var info = document.getElementById("registrationInfo");
buttonToHide.style.display = "none";
loader.innerHTML = "<div class='loader' style='margin:0 auto'></div>";
var i = 0;
  if (i == 0) {
    i = 1;
    var width = 1;
    var id = setInterval(frame, 1);
    function frame() {
      if (width >= 2000) {
        clearInterval(id);
        i = 0;
      } else {
      	if(width == 30) {
            info.innerHTML += "<br>CV processing...";
        }else if(width == 90) {
            info.innerHTML += "<br>Information extraction...";
        }else if(width == 200) {
            info.innerHTML += "<br>Predicting relevances...";
        }else if(width == 350) {
            info.innerHTML += "<br>Job offers sorting...";
        }else if(width == 500) {
            info.innerHTML += "<br>Almost done...";
        }
        width++;
      }
    }
  }
}