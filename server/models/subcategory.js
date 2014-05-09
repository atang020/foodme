var mysql = require('mysql');
var database = require('./database');

/**
 * Returns data for all subcategories. The callback gets two arguments (err, data).
 *
 * @param callback
 */
exports.getAll = function (callback) {
	database.query('SELECT * FROM subcategory', function (err, rows, fields) {
		if (err) {
			callback(err, null);
			return;
		}

		callback(null, rows);
	});
};

/**
 * Returns data for a specific subcategory. The callback gets two arguments (err, data).
 *
 * @param subcategoryId
 * @param callback
 */
exports.get = function (subcategoryId, callback) {
	database.query('SELECT * FROM subcategory WHERE subcategory_id = ?', [subcategoryId], function (err, rows, fields) {
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
 * Returns data for specified condition. The callback gets two arguments (err, data).
 *
 * @param params
 * @param callback
 */
exports.search = function (params, callback) {
	var y = database.query('SELECT * FROM subcategory WHERE ?', params, function (err, rows, fields) {
		if (err) {
			callback(err, null);
			return;
		}

		callback(null, rows);
	});
};
