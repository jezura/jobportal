addOption();

function addOption() {
  optionText = 'Lokalizováno: ' + geoplugin_region();
  optionValue = pairWithId();
  $('#region').append(`<option style='color: green' icon='fas fa-street-view' value="${optionValue}" selected>
  ${optionText}
  </option>`);}

function pairWithId() {
    var regionId;

    switch (geoplugin_region()) {
        case "Hlavni mesto Praha":
        regionId = "Kraj/19";
        break;
        case "Stredocesky kraj":
        regionId = "Kraj/27";
        break;
        case "Jihocesky kraj":
        regionId = "Kraj/35";
        break;
        case "Plzensky kraj":
        regionId = "Kraj/43";
        break;
        case "Karlovarsky kraj":
        regionId = "Kraj/51";
        break;
        case "Ustecky kraj":
        regionId = "Kraj/60";
        break;
        case "Liberecky kraj":
        regionId = "Kraj/78";
        break;
        case "Kralovehradecky kraj":
        regionId = "Kraj/86";
        break;
        case "Pardubicky kraj":
        regionId = "Kraj/94";
        break;
        case "Kraj Vysocina":
        regionId = "Kraj/108";
        break;
        case "Jihomoravsky kraj":
        regionId = "Kraj/116";
        break;
        case "Olomoucky kraj":
        regionId = "Kraj/124";
        break;
        case "Moravskoslezsky kraj":
        regionId = "Kraj/132";
        break;
        case "Zlinsky kraj":
        regionId = "Kraj/141";
        break;
    }

    return regionId;
}