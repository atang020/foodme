var database = require('./database');

function verify(orderItem) {
	// order_id, menu_item_id, and quantity must be NOT NULL
	if (orderItem.quantity === undefined || orderItem.quantity === null) {
		return new Error('A quantity must be provided.');
	}

	if (orderItem.order_id === undefined || orderItem.order_id === null) {
		return new Error('Order ID not specified.');
	}

	if (orderItem.menu_item_id === undefined || orderItem.menu_item_id === null) {
		return new Error('Menu Item ID not specified.');
	}

	return null;
}

/**
 * Returns data for all order items. The callback gets two arguments (err, data).
 *
 * @param callback
 */
exports.getAll = function (callback) {
	database.query('SELECT * FROM order_item', function (err, rows) {
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
	database.query('SELECT * FROM order_item WHERE order_item_id = ?', [orderId], function (err, rows) {
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
	database.query('SELECT * FROM order_item WHERE ?', params, function (err, rows) {
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
	var err = verify(data);
	if (err) {
		callback(err);
		return;
	}

	// if kitchenStatus is undefined, set to 0
	//TODO: WE NEED TO DECIDE WHAT THE DIFFERENT KITCHEN STATUS VALUES MEAN
	data.kitchen_status = data.kitchen_status === undefined ? 0 : data.kitchen_status;
	data.notes = data.notes === undefined ? '' : data.notes;

	database.query('INSERT INTO order_item (order_id, menu_item_id, quantity, notes, kitchen_status)' +
			'VALUES (?, ?, ?, ?, ?)', [data.order_id, data.menu_item_id, data.quantity, data.notes, data.kitchen_status],
		function (err, result) {
			if (err) {
				callback(err);
				return;
			}

			callback(null, result.insertId);
		});
};
