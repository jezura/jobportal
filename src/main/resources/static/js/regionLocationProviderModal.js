
var regionName = geoplugin_region();
addOptionToPersonalizationModal();

function addOptionToPersonalizationModal() {
  optionText = regionName;
  optionValue = pairWithId(regionName);
  $('#location').append(`<option style='color: green' icon='fas fa-street-view' value="${optionValue}" selected>
  ${optionText}
  </option>`);
  var found_region_name = document.getElementById("found-region-name");
  found_region_name.innerHTML = "<i style='color:green' class='fas fa-street-view'></i> " + regionName;
}