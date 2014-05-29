
/* 
 * Sets up the document
 *
 */
$(document).ready(function () {
	$('#loginError').hide();
	$('#loginForm').submit(function (event) {
	var loginData = $(this).serialize();
		$.ajax({
			data:$(this).serialize(),
			type: 'POST',
			url: '/api/users/login'
		}).done(function (data, textStatus) {
			setCookie('login', loginData);
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
			console.log('location: ' + window.location.pathname);
			if(window.location.pathname === '/') {
				console.log('auto login redirect');
				window.location.assign('/orders');
			}
		}).fail(function (textStatus, errorThrown) {
			console.log('auto-login failed');
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

