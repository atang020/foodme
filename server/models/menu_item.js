var mysql = require('mysql');
var database = require('./database');

/**
 * Returns data for all menu items. The callback gets two arguments (err, data).
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

/**
* Gets the menu_item with the specified id
* @param orderId 
* @param callback
*
*/
exports.get = function (orderId, callback) {
	database.query('SELECT * FROM menu_item WHERE order_item_id = ?', [orderId], function (err, rows, fields) {
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
* Gets the menu_items that match the given parameters
* @param params
* @param callback
*
*/
exports.search = function (params, callback) {
	var y = database.query('SELECT * FROM menu_item WHERE ?', params, function (err, rows, fields) {
		if (err) {
			callback(err, null);
			return;
		}
		callback(null, rows);
	});
};