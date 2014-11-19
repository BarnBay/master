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
	if($.cookie('User.Username') == null && $.cookie('User.Session') == null && $.cookie('User.Usertype') == null) {
		$('#mainNavbarRight').load('./content/nav_login.html');
	} else {
		$('#mainNavbarRight').load('./content/nav_account.html');
	}
	
	/*$('#mainNavbarRight').on('click', '#navSignIn', function(event) {
		$('#mainNavbarRight').load('./content/nav_account.html');
	});*/
		
	$('#mainNavbarRight').on('click', '#navSignOut', function(event) {
		$('#mainNavbarRight').load('./content/nav_login.html');
		$.removeCookie('User.Username');
		$.removeCookie('User.Session');
		$.removeCookie('User.Usertype');
		$.removeCookie('User.JSON');
		location.reload();
	});
});


/* ------------------------------------------------------------------------------------
   Navigation - By Clicking on View Cart, go to Cart.html
   ------------------------------------------------------------------------------------ */
$(document).ready(function() {
	$('#mainNavbarRight').on('click', '#navViewShoppingCartButton', function(event) {
		$('#mainContainer').hide().load('./content/cart.html').fadeIn('500');
	});

	$('#mainNavbarRight').on('click', '#navCheckOutButton', function(event) {
		  $('#mainContainer').hide().load('./content/orderConfirmation.html').fadeIn('500');
		});
	
	$('#mainContainer').on('click', '#checkout', function(event) {
		$('#mainContainer').hide().load('./content/orderConfirmation.html').fadeIn('500');
	});
});

/* ------------------------------------------------------------------------------------
Navigation Login - By Clicking on Register, go to register.html
------------------------------------------------------------------------------------ */
$(document).ready(function() {
	$('#mainNavbarRight').on('click', '#registerForm', function(event) {
		$('.nav li').removeClass('active');
		$('#mainContainer').hide().load('./content/nav_register.html').fadeIn('500');
	});
});


/* ------------------------------------------------------------------------------------
Navigation Login - By Clicking on Help, go to nav_help.html
------------------------------------------------------------------------------------ */
$(document).ready(function() {
	$('#mainNavbarRight').on('click', '#helpForm', function(event) {
		$('.nav li').removeClass('active');
		$('#mainContainer').hide().load('./content/nav_help.html').fadeIn('500');
	});
});


/* ------------------------------------------------------------------------------------
Navigation Login - By Clicking on Dashboard, go to Dashboard.html
------------------------------------------------------------------------------------ */
$(document).ready(function() {
	$('#mainNavbarRight').on('click', '#dashboard', function(event) {
		$('#mainContainer').hide().load('./content/dashboard.html').fadeIn('500');
		event.preventDefault();
	});
});

/* ------------------------------------------------------------------------------------
Navigation Login - By Clicking on Orders, go to customer_orders.html
------------------------------------------------------------------------------------ */
$(document).ready(function() {
	$('#mainNavbarRight').on('click', '#customer_orders', function(event) {
		$('#mainContainer').hide().load('./content/customer_orders.html').fadeIn('500');
		event.preventDefault();
	});
});

/* ------------------------------------------------------------------------------------
Navigation Login - By Clicking on settings, go to settings.html
------------------------------------------------------------------------------------ */
$(document).ready(function() {
	$('#mainNavbarRight').on('click', '#settings', function(event) {
		$('#mainContainer').hide().load('./content/settings.html').fadeIn('500');
		event.preventDefault();
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
   Product List View
   ------------------------------------------------------------------------------------ */
$(document).ready(function() {
	/* On click filter Products */
	/* Have a look at nav_products_list.html */
	
	/* On click of ListView Icon */
	$('#mainContainer').on('click', '#productList', function(event) {
		event.preventDefault();
		$('#productGrid').removeClass('active');
		$('#productList').addClass('active');
		
		$('#products .item').addClass('list-group-item');
	});
	
	/* On click of GridView Icon */
	$('#mainContainer').on('click', '#productGrid', function(event) {
		event.preventDefault();
		$('#productList').removeClass('active');
		$('#productGrid').addClass('active');
		
		$('#products .item').removeClass('list-group-item');
		$('#products .item').addClass('grid-group-item');
	});
	
	/* On changed value of selector -> Sort JSON-Object ProductListJSONObject */
	$('#mainContainer').on('change', '#selectNavProdutsList', function(event) {
		var selectValue = this.value.split(",");
		var prop = selectValue[0];
		
		if(selectValue[1] == "ASC") {
			var asc = true;
		} else {
			var asc = false;
		}
		
		ProductListJSONObject = sortJSON(ProductListJSONObject, prop, asc);	

		productListFunction(ProductListJSONObject);
	});
		
	/* On click of Item */
	$('#mainContainer').on('click', '#productDetailView', function(event) {
		$('#mainContainer').hide().load('./content/product_chris.html').fadeIn('500');
	});
});


/* ------------------------------------------------------------------------------------
   Farmer List View
   ------------------------------------------------------------------------------------ */
$(document).ready(function() {
	/* On click of ListView Icon */
	$('#mainContainer').on('click', '#farmerList', function(event) {
		event.preventDefault();
		$('#farmerGrid').removeClass('active');
		$('#farmerList').addClass('active');
		
		$('#farmers .item').addClass('list-group-item');
	});
	
	/* On click of GridView Icon */
	$('#mainContainer').on('click', '#farmerGrid', function(event) {
		event.preventDefault();
		$('#farmerList').removeClass('active');
		$('#farmerGrid').addClass('active');
		
		$('#farmers .item').removeClass('list-group-item');
		$('#farmers .item').addClass('grid-group-item');
	});
	
	/* On changed value of selector -> Sort JSON-Object ProductListJSONObject */
	$('#mainContainer').on('change', '#selectNavFarmersList', function(event) {
		var selectValue = this.value.split(",");
		var prop = selectValue[0];
		
		if(selectValue[1] == "ASC") {
			var asc = true;
		} else {
			var asc = false;
		}
		
		FarmerListJSONObject = sortJSON(FarmerListJSONObject, prop, asc);	
		
		farmerListFunction(FarmerListJSONObject);
	});
	
	/* On click of Item */
	$('#mainContainer').on('click', '#farmerDetailView', function(event) {
		$('#mainContainer').hide().load('./content/farmer_chris.html').fadeIn('500');
	});
	
	/*
	 *  On Click of Dashboard Item
	 *  dash_viewDetails
	 *  dash_NewOrders
	 */
	$('#mainContainer').on('click', '#dash_stock', function(event) {
		$('#dashMain').hide().load('./content/dash_stock.html').fadeIn('500');
		event.preventDefault();
	});
	
	$('#mainContainer').on('click', '#dash_viewDetails', function(event) {
		$('#dashMain').hide().load('./content/viewDetailsMockUp.html').fadeIn('500');
		event.preventDefault();
	});
	
	$('#mainContainer').on('click', '#dash_NewOrders', function(event) {
		$('#dashMain').hide().load('./content/newOrder.html').fadeIn('500');
		event.preventDefault();
	});
	
	$('#mainContainer').on('click', '#profil', function(event) {
		$('#dashMain').hide().load('./content/profil.html').fadeIn('500');
		event.preventDefault();
	});
});


/* ------------------------------------------------------------------------------------
   Location List View
   ------------------------------------------------------------------------------------ */
$(document).ready(function() {
	/* On click of ListView Icon */
	$('#mainContainer').on('click', '#locationList', function(event) {
		event.preventDefault();
		$('#locationGrid').removeClass('active');
		$('#locationList').addClass('active');
		
		$('#locations .item').addClass('list-group-item');
	});
	
	/* On click of GridView Icon */
	$('#mainContainer').on('click', '#locationGrid', function(event) {
		event.preventDefault();
		$('#locationList').removeClass('active');
		$('#locationGrid').addClass('active');
		
		$('#locations .item').removeClass('list-group-item');
		$('#locations .item').addClass('grid-group-item');
	});
	
	/* On changed value of selector -> Sort JSON-Object ProductListJSONObject */
	$('#mainContainer').on('change', '#selectNavLocationsList', function(event) {
		var selectValue = this.value.split(",");
		var prop = selectValue[0];
		
		if(selectValue[1] == "ASC") {
			var asc = true;
		} else {
			var asc = false;
		}
		
		LocationListJSONObject = sortJSON(LocationListJSONObject, prop, asc);	
		
		locationListFunction(LocationListJSONObject);
	});
	
	
	/* On click of Item */
	/*$('#mainContainer').on('click', '#farmerDetailView', function(event) {
		$('#mainContainer').hide().load('./content/farmer_chris.html').fadeIn('500');
	});*/
});


/* ------------------------------------------------------------------------------------
   Sorting-Algorithm
   ------------------------------------------------------------------------------------ */
function sortJSON(data, key, asc) {		
    return data.sort(function (a, b) {
        var x = a[key];
        var y = b[key];
		if(asc) {
			return ((x < y) ? -1 : ((x > y) ? 1 : 0));
		} else {
			return ((y < x) ? -1 : ((y > x) ? 1 : 0));
		}
    });
}
