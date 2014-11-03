/* ------------------------------------------------------------------------------------
   First visit, load default
   ------------------------------------------------------------------------------------ */
$(document).ready(function() {
	if($('#mainHome').hasClass('active')) {
		$('#mainContainer').hide().load('./content/nav_home.html').fadeIn('500');
		$('#mainFooter').hide().fadeIn('500');
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
		$('#mainContainer').hide().load('./content/' + $content + '.html').fadeIn('500');
		$('#mainFooter').hide().fadeIn('500');
        e.preventDefault();
    });
	
	//OnCLick for the Rest of the Navigation
	$('.nav li a').click(function(e) {
		$('.nav li').removeClass('active');
		
		var $parent = $(this).parent();
		var $content = $(this).attr('href');
		if (!$parent.hasClass('active')) {
			$parent.addClass('active');
			
			$('#mainContainer').hide().load('./content/' + $content + '.html').fadeIn('500');
			$('#mainFooter').hide().fadeIn('500');
        }
        e.preventDefault();
    });
	
	//OnCLick for the Footer-Navigation
	$('#mainFooterNavigation li a').click(function(e) {
		$('#mainFooterNavigation li').removeClass('active');
		
		var $parent = $(this).parent();
		var $content = $(this).attr('href');
		$('#mainContainer').hide().load('./content/' + $content + '.html').fadeIn('500');
		$('#mainFooter').hide().fadeIn('500');
        e.preventDefault();
    });
	
});


/* ------------------------------------------------------------------------------------
   Navigation - Change the Login to Account, if User is logged in
   ------------------------------------------------------------------------------------ */
$(document).ready(function() {
	$('#mainNavbarRight').load('./content/nav_login.html');
	
	$('#mainNavbarRight').on('click', '#navSignIn', function(event) {
		$('#mainNavbarRight').load('./content/nav_account.html');
	});
	
	$('#mainNavbarRight').on('click', '#navSignOut', function(event) {
		$('#mainNavbarRight').load('./content/nav_login.html');
	});
});


/* ------------------------------------------------------------------------------------
   Navigation - By Clicking on View Cart, go to Cart.html
   ------------------------------------------------------------------------------------ */
$(document).ready(function() {
	$('#mainNavbarRight').on('click', '#navViewShoppingCartButton', function(event) {
		$('#mainContainer').load('./content/cart.html');
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


/* ------------------------------------------------------------------------------------
   Product Change List to Grid or Grid to List
   ------------------------------------------------------------------------------------ */
$(document).ready(function() {
	$('#mainContainer').on('click', '#productList', function(event) {
		event.preventDefault();
		$('#productGrid').removeClass('active');
		$('#productList').addClass('active');
		
		$('#products .item').addClass('list-group-item');
	});
	
	$('#mainContainer').on('click', '#productGrid', function(event) {
		event.preventDefault();
		$('#productList').removeClass('active');
		$('#productGrid').addClass('active');
		
		$('#products .item').removeClass('list-group-item');
		$('#products .item').addClass('grid-group-item');
	});
});