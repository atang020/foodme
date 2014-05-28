$(document).ready(function () {
	$('#loginError').hide();

	$('#loginForm').submit(function (event) {
		$.ajax({
			data: $(this).serialize(),
			type: 'POST',
			url: '/api/users/login'
		}).done(function (data, textStatus) {
			window.location.assign('/orders');
		}).fail(function (textStatus, errorThrown) {
			$('#loginError').stop().fadeIn(100).fadeOut(3000).text('Invalid email/password');
		});
		event.preventDefault();
	});
});

//logs the user out
function logout() {
	window.location.assign('/');
}
