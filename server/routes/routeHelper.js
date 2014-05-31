var userModel = require('../models/userModel');

exports.isValidUser = function (email, password, callback) {
	if (email && password) {
		userModel.search({email: email}, function (err, user) {
			if (err) {
				return callback(err);
			}

			if (user.length === 0) {
				return callback(null, false, null);
			}

			if (user[0].password === password) {
				return callback(null, true, user[0]);
			} else {
				return callback(null, false, null);
			}
		});
	} else {
		callback(null, false, null);
	}
};

exports.redirectIfLoggedOut = function (req, res, callback) {
	routeHelper.isValidUser(req.cookies.email, req.cookies.password, function (err, isUser, user) {
		if (err || !isUser) {
			res.redirect('/');
			callback(false);
		}
		else {
			callback(true);
		}
	});
};

exports.redirectIfLoggedIn = function (req, res, callback) {
	routeHelper.isValidUser(req.cookies.email, req.cookies.password, function (err, isUser, user) {
		if (err || !isUser) {
			callback(false);
		}
		else {
			callback(true);
			res.redirect('/orders');
		}
	});
};

exports.jsonError = function (res, err) {
	res.status(err.status || 500);
	res.send(JSON.stringify(err, undefined, 2));
};

exports.htmlError = function (res, err) {
	res.status(err.status || 500);
	res.render('error', {
		message: err.message,
		error: err
	});
};