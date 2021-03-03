$( function() {
  $( "#emailSearch" ).autocomplete({
    source: "/emailsAutocomplete",
    minLength: 2
  });
  });

$( function() {
           $( "#fullNameSearch" ).autocomplete({
             source: "/fullNamesAutocomplete",
             minLength: 2
           });
         });

$( function() {
  $( "#titleSearch" ).autocomplete({
    source: "/titlesAutocomplete",
    minLength: 2
  });
});

$( function() {
  $( "#titleAndEmployerSearch" ).autocomplete({
    source: "/titlesAndEmployersAutocomplete",
    minLength: 2
  });
});
