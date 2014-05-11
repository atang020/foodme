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
 * @param order the data to be inserted into the order table
 * @param callback
 */
exports.add = function (order, callback) {
	var err = verify(order);
	if (err) {
		callback(err);
		return;
	}

	// if checked_out or call_waiter_status are undefined, set to 0
	order.checked_out = order.checked_out === undefined ? 0 : order.checked_out;
	order.call_waiter_status = order.call_waiter_status === undefined ? 0 : order.call_waiter_status;

	database.query('INSERT INTO order (table_number, checked_out, call_waiter_status)' +
			'VALUES (?, ?, ?)', [order.table_number, order.checked_out, order.call_waiter_status],
		function (err, result) {
			if (err) {
				callback(err);
				return;
			}

			callback(null, result.insertId);
		});
};

exports.update = function (order, callback) {
	var err = verify(order);
	if (err) {
		callback(err);
		return;
	}

	database.query('UPDATE order SET table_number = ?, order_date = ?, checked_out = ?, call_waiter_status = ? WHERE order_id = ?',
		[order.table_number, order.order_date, order.checked_out, order.call_waiter_status, order.order_id], function (err) {

			if (err) {
				callback(err);
				return;
			}
			callback(null);
		});
};
