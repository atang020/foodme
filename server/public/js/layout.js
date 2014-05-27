//logs the user out
function logout() {
	window.location.assign('/');
}

//when the login submit button is pressed
function loginClicked() {
	var email = $('#emailText').val();
	var password = $('#passwordText').val();
	var status = $('#loginStatus');
	status.text('');
	login(email, password);
}

//try to login with this email and password
function login(email, password) {
	if (password === 'gary') {
		window.location.assign('/orders');
	}
	else {
		var status = $('#loginStatus');
		status.show();
		status.text('incorrect user/password');
		status.removeClass('hidden');
		status.fadeOut(3000);
	}
}
