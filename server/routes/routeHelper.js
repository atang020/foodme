var userModel = require('../models/userModel');

exports.isValidUser = function (req, callback) {
	if (req.cookies.id && req.cookies.password) {
		userModel.get(req.cookies.id, function (err, user) {
			if (err) {
				callback(err);
				return;
			}

			if(user.password === req.cookies.password) {
				callback(null, true, user);
				return;
			}
		});
	}
	callback(null, false, null);
};