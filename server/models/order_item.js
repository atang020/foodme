var mysql = require('mysql');
var database = require('./database');

/**
 * Returns data for all order items. The callback gets two arguments (err, data).
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
* Gets the order_item with the specified id
* @param orderId
* @param callback
*
*/
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

/**
* Gets the order_items that match the given parameters
* @param params
* @param callback
*
*/
exports.search = function (params, callback) {
	var y = database.query('SELECT * FROM order_item WHERE ?', params, function (err, rows, fields) {
		if (err) {
			callback(err, null);
			return;
		}
		callback(null, rows);
	});
};

/**
 * Inserts a new order_item. The callback gets two arguments (err, data).
 *
 * @param data the data to be inserted into the order_item table
 * @param callback
 */
exports.add = function (data, callback) {
    // order_id, menu_item_id, and quantity must be NOT NULL
    if (data.quantity === undefined || data.quantity === null) {
        callback(new Error("Quantity must be defined."));
        return;
    }

    // if kitchenStatus is undefined, set to 0
    // WE NEED TO DECIDE WHAT THE DIFFERENT KITCHEN STATUS VALUES MEAN
    data.kitchenStatus = data.kitchenStatus === undefined ? 0 : data.kitchenStatus;
    data.notes = data.notes === undefined ? "" : data.notes;

    database.query('INSERT INTO order_item (order_id, menu_item_id, quantity, notes, kitchen_status)' +
                   'VALUES (?, ?, ?, ?, ?)', [data.orderId, data.menuItemId, data.quantity, data.notes, data.kitchenStatus],
                    function (err, result) {
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
};
