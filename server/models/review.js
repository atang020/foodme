var database = require('./database');

function verify(review) {
	// rating must be NOT NULL
	if (review.rating === undefined || review.rating === null) {
		return new Error('The review must have a rating.');
	}
	if (review.reviewer > 45) {
		return new Error('Reviewer name mmsut be less than 45 characters.');
	}

	return null;
}

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
	var err = verify(data);
	if (err) {
		callback(err);
		return;
	}

	// if reviewer is undefined, set to 'Anonymous:
	data.reviewer = data.reviewer === undefined ? 'Anonymous' : data.reviewer;
	// if no review text, set to null
	data.review_text = data.review_text === undefined ? null : data.review_text;

	database.query('INSERT INTO review (menu_item_id, reviewer, rating, review_text)' +
			'VALUES (?, ?, ?, ?)', [data.menu_item_id, data.reviewer, data.rating, data.review_text],
		function (err, result) {
			if (err) {
				callback(err);
				return;
			}

			callback(null, result.insertId);
		});
};
