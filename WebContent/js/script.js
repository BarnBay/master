/* ------------------------------------------------------------------------------------
   First visit, load default
   ------------------------------------------------------------------------------------ */
$(document).ready(function() {
	if($('#mainHome').hasClass('active')) {
		$('#mainContainer').load('./content/home.html');
	}
});


/* ------------------------------------------------------------------------------------
   Navigation - Change the content by onClick
   ------------------------------------------------------------------------------------ */
$(document).ready(function () {
	//OnClick for the NavBar-Brand
	$('.navbar-brand').click(function(e) {	
		$('.nav li').removeClass('active');
		$('#mainHome').addClass('active');
		
		var $content = $(this).attr('href');
		$('#mainContainer').load('./content/' + $content + '.html');
        e.preventDefault();
    });
	
	//OnCLick for the Rest of the Navigation
	$('.nav li a').click(function(e) {
		$('.nav li').removeClass('active');
		
		var $parent = $(this).parent();
		var $content = $(this).attr('href');
		if (!$parent.hasClass('active')) {
			$parent.addClass('active');
			$('#mainContainer').load('./content/' + $content + '.html');
        }
        e.preventDefault();
    });
});

/* ------------------------------------------------------------------------------------
   Start Coursel automatically
   ------------------------------------------------------------------------------------ */
$(document).ready(function() {
     $('#homeCarousel').carousel({
         interval: 4500
     })
});