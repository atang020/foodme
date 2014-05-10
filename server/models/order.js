var mysql = require('mysql');
var database = require('./database');

/**
 * Returns data for all users. The callback gets two arguments (err, data).
 *
 * @param callback
 */
exports.getAll = function (callback) {
	database.query('SELECT * FROM order', function (err, rows, fields) {
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
	database.query('SELECT * FROM order WHERE order_id = ?', [orderId], function (err, rows, fields) {
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
	var y = database.query('SELECT * FROM user WHERE ?', params, function (err, rows, fields) {
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
	// table_number must be NOT NULL
	if (data.tableNumber === undefined || data.tableNumber === null) {
		callback(new Error("Table number must be defined."));
		return;
	}

	// if checked_out or call_waiter_status are undefined, set to 0
	data.checkedOut = data.checkedOut === undefined ? 0 : data.checkedOut;
	data.callWaiterStatus = data.callWaiterStatus === undefined ? 0 : data.callWaiterStatus;

	database.query('INSERT INTO order (table_number, checked_out, call_waiter_status)' +
			'VALUES (?, ?, ?)', [data.tableNumber, data.checkedOut, data.callWaiterStatus],
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
