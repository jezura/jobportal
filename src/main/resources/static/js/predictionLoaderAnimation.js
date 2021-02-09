function loading() {
var loader = document.getElementById("loader");
var info = document.getElementById("info");
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
      	if(width == 50) {
            info.innerHTML += "<br>CV processing...";
        }else if(width == 150) {
            info.innerHTML += "<br>Information extraction...";
        }else if(width == 400) {
            info.innerHTML += "<br>Predicting relevances...";
        }else if(width == 600) {
            info.innerHTML += "<br>Job offers sorting...";
        }else if(width == 1200) {
            info.innerHTML += "<br>Almost done...";
        }
        width++;
      }
    }
  }
}