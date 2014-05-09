var mysql = require('mysql');
var database = require('./database');

/**
 * Returns data for all users. The callback gets two arguments (err, data).
 *
 * @param callback
 */
exports.getAll = function (callback) {
	database.query('SELECT * FROM user', function (err, rows, fields) {
		if (err) {
			callback(err, null);
			return;
		}

		callback(null, rows);
	});
};

data.phone = data.phone === undefined ? null : data.phone;

/**
 * Returns a user selected by userId. The callback gets two arguments (err, data).
 *
 * @param userId the user ID to search by
 * @param callback a function called upon getting the data
 */
exports.get = function (userId, callback) {
	database.query('SELECT * FROM user WHERE user_id = ?', [userId], function (err, rows, fields) {
		if (err) {
			callback(err, null);
			return;
		}

		if (rows === []) {
			callback(null, null);
			return;
		}

		callback(null, rows[0]);
	});
};

/**
 *
 *
 * @param params an object of params to search. The keys refer to the column name and the values
 * refer to the value.
 * @param callback
 */
exports.search = function (params, callback) {
	var y = database.query('SELECT * FROM user WHERE ?', params, function (err, rows, fields) {
		if (err) {
			callback(err, null);
			return;
		}

		callback(null, rows);
	});
};