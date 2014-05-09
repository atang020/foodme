var mysql = require('mysql');
var database = require('./database');

/**
 * Returns data for all orders. The callback gets two arguments (err, data).
 *
 * @param callback
 */
exports.getAll = function (callback) {
	database.query('SELECT * FROM order_item', function (err, rows, fields) {
		if (err) {
			callback(err, null);
			return;
		}
		callback(null, rows);
	});
};

exports.get = function (orderId, callback) {
	database.query('SELECT * FROM order_item WHERE order_item_id = ?', [orderId], function (err, rows, fields) {
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
	var y = database.query('SELECT * FROM order_item WHERE ?', params, function (err, rows, fields) {
		if (err) {
			callback(err, null);
			return;
		}
		callback(null, rows);
	});
};