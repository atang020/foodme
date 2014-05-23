var userModel = require('../models/userModel');

exports.isValidUser = function (req) {
	if (req.cookies.id && req.cookies.password) {
		userModel.get(req.cookies.id, function (err, user) {
			if (err) {
				return false;
			}

			return user.password === req.cookies.password;
		});
	} else {
		return false;
	}
};