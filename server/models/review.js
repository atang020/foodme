var database = require('./database');

/**
 * Returns data for review database. The callback gets two arguments (err, data).
 *
 * @param callback
 */
exports.getAll = function (callback) {
	database.query('SELECT * FROM review', function (err, rows) {
		if (err) {
			callback(err, null);
			return;
		}

		callback(null, rows);
	});
};

exports.get = function (reviewId, callback) {
	database.query('SELECT * FROM review WHERE review_id = ?', [reviewId], function (err, rows) {
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

exports.search = function (params, callback) {
	database.query('SELECT * FROM review WHERE ?', params, function (err, rows) {
		if (err) {
			callback(err, null);
			return;
		}

		callback(null, rows);
	});
};

/**
 * Inserts a new review into the database. The callback gets two arguments (err, data).
 *
 * @param data the data to be inserted into the review table
 * @param callback
 */
exports.add = function (data, callback) {
	// rating must be NOT NULL
	if (data.rating === undefined || data.rating === null) {
		callback(new Error('Rating must be defined.'));
		return;
	}
	if (data.reviewer > 45) {
		callback(new Error('Reviewer must be less than 45 characters'));
		return;
	}

	// if reviewer is undefined, set to 'Anonymous:
	data.reviewer = data.reviewer === undefined ? 'Anonymous' : data.reviewer;
	// if no review text, set to null
	data.reviewText = data.reviewText === undefined ? null : data.reviewText;

	database.query('INSERT INTO review (menu_item_id, reviewer, rating, review_text)' +
			'VALUES (?, ?, ?, ?)', [data.menuItemId, data.reviewer, data.rating, data.reviewText],
		function (err, result) {
			if (err) {
				callback(err);
				return;
			}

			callback(null, result.insertId);
		});
};
