
/* 
 * Sets up the document
 *
 */
$(document).ready(function () {
	$('#loginError').hide();
	if(getCookie('email') !== '') {
		$('#signInName').text(getCookie('email'));
	}
	$('#loginForm').submit(function (event) {
	var loginData = $(this).serialize();
		$.ajax({
			data:$(this).serialize(),
			type: 'POST',
			url: '/api/users/login'
		}).done(function (data, textStatus) {
			setCookie('login', loginData);
			setCookie('email', loginData.split('=')[1].split('&')[0].replace('%40', '@'));
			window.location.assign('/orders');
		}).fail(function (textStatus, errorThrown) {
			$('#loginError').stop().fadeIn(100).fadeOut(3000).text('Invalid email/password');
		});
		event.preventDefault();
	});
});

window.onload =  function () {
	var login = getCookie('login');
	 
	 if(login !== '' ) {
		$.ajax({
			data: login,
			type: 'POST',
			url: '/api/users/login'
		}).done(function (data, textStatus) {
			if(window.location.pathname === '/') {
				window.location.assign('/orders');
			}
		}).fail(function (textStatus, errorThrown) {
			if(window.location.pathname !== '/') {
				window.location.assign('/');
			}
				
		});
	 }
	 else {
		if(window.location.pathname !== '/') {
				window.location.assign('/');
			}
	 }
	 
};




//logs the user out
function logout() {
	setCookie('login', '')
	window.location.assign('/');
}

function getCookie(cname)
{
	var name = cname + "=";
	var ca = document.cookie.split(';');
	for(var i=0; i<ca.length; i++) {
		var c = ca[i].trim();
		if (c.indexOf(name)==0) return c.substring(name.length,c.length);
		}
	return "";
}
function setCookie(cname,cvalue)
{
	document.cookie = cname + "=" + cvalue + ";" 
}

