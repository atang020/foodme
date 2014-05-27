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

			if(user[0].password === password) {
				return callback(null, true, user[0]);
			} else {
				return callback(null, false, null);
			}
		});
	} else {
		callback(null, false, null);
	}
};