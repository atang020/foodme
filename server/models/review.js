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
};

/**
 * Returns data for review database. The callback gets two arguments (err, data).
 *
 * @param callback
 */
exports.getAll = function (callback) {
	database.query('SELECT * FROM review', null, function (err, rows) {
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
 * @param review the data to be inserted into the review table
 * @param callback
 */
exports.add = function (review, callback) {
	var err = verify(review);
	if (err) {
		callback(err);
		return;
	}


	// if reviewer is undefined, set to 'Anonymous:
	review.reviewer = review.reviewer === undefined ? 'Anonymous' : review.reviewer;
	// if no review text, set to null
	review.review_text = review.review_text === undefined ? null : review.review_text;

	database.query('INSERT INTO review (menu_item_id, reviewer, rating, review_text)' +
			'VALUES (?, ?, ?, ?)', [review.menu_item_id, review.reviewer, review.rating, review.review_text],
		function (err, result) {
			if (err) {
				callback(err);
				return;
			}

			callback(null, result.insertId);
		});
};

exports.update = function (review, callback) {
	var err = verify(review);
	if (err) {
		callback(err);
		return;
	}

	database.query('UPDATE review SET menu_item = ?, reviewer = ?, rating = ?, review_text = ?, review_date = ? WHERE review_id = ?',
		[review.menu_item, review.reviewer, review.rating, review.review_text, review.review_date, review.review_id], function (err) {

			if (err) {
				callback(err);
				return;
			}
			callback(null);
		});
};

/**
 * Deletes a review. The callback gets two arguments (err, data).
 *
 * @param review the data to be deleted from the review table
 * @param callback
 */
exports.delete = function (review, callback) {
    if (review.review_id === null) {
        callback(new error ('Invalid review: no id present'));
        return;
    }

    database.query('DELETE FROM reivew WHERE review_id = ?', review.review_id, function (err) {
        if (err) {
            callback(err);
            return;
        }
        callback(null);
    });
};
