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

/**
 * Returns data for specific userId. The callback gets two arguments (err, data).
 *
 * @param callback
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
 * Returns data for specific condition. The callback gets two arguments (err, data).
 *
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


/**
 * Inserts data into database. The callback gets two arguments (err, data).
 *
 * @param callback
 */
exports.add = function (data, callback) {
    if (data.password === undefined || data.password === null) {
        callback(new Error("Password must be defined."));
        return;
    }
    if (data.password.length > 256) {
        callback(new Error("Password must be less than 256 characters."));
        return;
    }
    if (data.email === undefined || data.email === null) {
        callback(new Error("Password must be defined."));
        return;
    }
    if (data.email.length > 256) {
        callback(new Error("Email must be less than 256 characters."));
        return;
    }
    // If data.phone is undefined set to null
    data.phone = data.phone === undefined ? null : data.phone;

    database.query('INSERT INTO user (password, email, phone) VALUES (?, ?, ?)', [data.password, data.email, data.phone], function (err, result) {
        if (err) {
            database.rollback(function () {
                callback(err);
                return;
            });
        }

        database.commit(function(err) {
            if (err) {
                connection.rollback(function() {
                    callback(err);
                    return;
                });
            }
            callback(null, result.insertId);
        });
    });
}
