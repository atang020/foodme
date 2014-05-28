var database = require('./database');

function verify(user) {
	if (user.password === undefined || user.password === null) {
		return new Error('The password field is required.');
	}
	if (user.password.length > 256) {
		return new Error('Password must be less than 256 characters.');
	}
	if (user.email === undefined || user.email === null) {
		return new Error('The email field is required.');
	}
	if (user.email.length > 256) {
		return new Error('Email must be less than 256 characters.');
	}
	return null;
}

/**
 * Returns data for all users. The callback gets two arguments (err, data).
 *
 * @param callback
 */
exports.getAll = function (callback) {
	database.query('SELECT * FROM user', null, function (err, rows) {
		if (err) {
			callback(err, null);
			return;
		}

		callback(null, rows);
	});
};

/**
 * Returns a user selected by userId. The callback gets two arguments (err, data).
 *
 * @param userId the user ID to search by
 * @param callback a function called upon getting the data
 */
exports.get = function (userId, callback) {
	database.query('SELECT * FROM user WHERE user_id = ?', [userId], function (err, rows) {
		if (err) {
			callback(err, null);
			return;
		}

		if (rows.length === 0) {
			callback(null, null);
			return;
		}

		callback(null, rows[0]);
	});
};

/**
 * Returns data for specific condition. The callback gets two arguments (err, data).
 *
 * @param params an object of params to search. The keys refer to the column name and the values
 * refer to the value.
 * @param callback
 */
exports.search = function (params, callback) {
	database.query('SELECT * FROM user WHERE ?', params, function (err, rows) {
		if (err) {
			callback(err, null);
			return;
		}
		callback(null, rows);
	});
};


/**
 * Inserts a new user into database. The callback gets two arguments (err, data).
 *
 * @param user the data to be inserted into the user table
 * @param callback
 */
exports.add = function (user, callback) {
	var err = verify(user);
	if (err) {
		callback(err);
		return;
	}

	// If data.phone is undefined set to null
	user.phone = user.phone === undefined ? null : user.phone;

	database.query('INSERT INTO user (password, email, phone) VALUES (?, ?, ?)',
		[user.password, user.email, user.phone], function (err, result) {
			if (err) {
				callback(err);
				return;
			}

			callback(null, result.insertId);
		});
};

exports.update = function (user, callback) {
	var err = verify(user);
	if (err) {
		callback(err);
		return;
	}

	database.query('UPDATE user SET password = ?, email = ?, phone = ? WHERE user_id = ?',
		[user.password, user.email, user.phone, user.user_id], function (err) {

			if (err) {
				callback(err);
				return;
			}
			callback(null);
		});
};

/**
 * Deletes a user. The callback gets two arguments (err, data).
 *
 * @param user the data to be deleted from the user table
 * @param callback
 */
exports.remove = function (user, callback) {
	var id = null;

	if (typeof user === 'object') {
		id = user.user_id;
	} else {
		id = user;
	}

	if (id === null) {
		callback(new Error('Invalid user: no id present'));
		return;
	}

	database.query('DELETE FROM user WHERE user_id = ?', id, function (err) {
		if (err) {
			callback(err);
			return;
		}
		callback(null);
	});
};
