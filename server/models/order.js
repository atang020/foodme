var database = require('./database');

function verify(order) {
	// table_number must be NOT NULL
	if (order.table_number === undefined || order.table_number === null) {
		return new Error('No table number specified.');
	}

	return null;
}

/**
 * Returns data for all users. The callback gets two arguments (err, data).
 *
 * @param callback
 */
exports.getAll = function (callback) {
	database.query('SELECT * FROM order', function (err, rows) {
		if (err) {
			callback(err, null);
			return;
		}

		callback(null, rows);
	});
};

/**
 * Returns data for all specific user. The callback gets two arguments (err, data).
 *
 * @param orderId
 * @param callback
 */
exports.get = function (orderId, callback) {
	database.query('SELECT * FROM order WHERE order_id = ?', [orderId], function (err, rows) {
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
 * Returns data for all users under specified condition. The callback gets two arguments (err, data).
 *
 * @param params
 * @param callback
 *
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
 * Inserts a new order into the database. The callback gets two arguments (err, data).
 *
 * @param data the data to be inserted into the order table
 * @param callback
 */
exports.add = function (data, callback) {
	var err = verify(data);
	if (err) {
		callback(err);
		return;
	}

	// if checked_out or call_waiter_status are undefined, set to 0
	data.checked_out = data.checked_out === undefined ? 0 : data.checked_out;
	data.call_waiter_status = data.call_waiter_status === undefined ? 0 : data.call_waiter_status;

	database.query('INSERT INTO order (table_number, checked_out, call_waiter_status)' +
			'VALUES (?, ?, ?)', [data.table_number, data.checked_out, data.call_waiter_status],
		function (err, result) {
			if (err) {
				callback(err);
				return;
			}

			callback(null, result.insertId);
		});
};
