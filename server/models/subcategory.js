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

/**
 * Inserts a new subcategory into the database. The callback gets two arguments (err, data).
 *
 * @param data the data to be inserted into the subcategory table
 * @param callback
 */
exports.add = function (data, callback) {
	//  name and category must be NOT NULL
	if (data.name === undefined || data.name === null) {
		callback(new Error("Name must be defined."));
		return;
	}
	if (data.name > 32) {
		callback(new Error("Name must be less than 32 characters"));
		return;
	}
	if (data.category === undefined || data.category === null) {
		callback(new Error("Category must be defined."));
		return;
	}

	database.query('INSERT INTO subcategory (name, category) VALUES (?, ?)', [data.name, data.category],
		function (err, result) {
			if (err) {
				database.rollback(function () {
					callback(err);
					return;
				});
			}

			database.commit(function (err) {
				if (err) {
					database.rollback(function () {
						callback(err);
						return;
					});
				}
				callback(null, result.insertId);
			});
		});
};
