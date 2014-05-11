var database = require('./database');

function verify(subcategory) {
	//  name and category must be NOT NULL
	if (subcategory.name === undefined || subcategory.name === null) {
		return new Error('Subcategory must have a name.');
	}
	if (subcategory.name > 32) {
		return new Error('Subcategory name must be less than 32 characters.');
	}
	if (subcategory.category === undefined || subcategory.category === null) {
		return new Error('Category must be defined.');
	}
	return null;
}

/**
 * Returns data for all subcategories. The callback gets two arguments (err, data).
 *
 * @param callback
 */
exports.getAll = function (callback) {
	database.query('SELECT * FROM subcategory', function (err, rows) {
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
	database.query('SELECT * FROM subcategory WHERE subcategory_id = ?', [subcategoryId], function (err, rows) {
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
	database.query('SELECT * FROM subcategory WHERE ?', params, function (err, rows) {
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
 * @param subcategory the data to be inserted into the subcategory table
 * @param callback
 */
exports.add = function (subcategory, callback) {
	var err = verify(subcategory);
	if (err) {
		callback(err);
		return;
	}

	database.query('INSERT INTO subcategory (name, category) VALUES (?, ?)', [subcategory.name, subcategory.category],
		function (err, result) {
			if (err) {
				callback(err);
				return;
			}

			callback(null, result.insertId);
		});
};
