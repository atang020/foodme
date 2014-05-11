var database = require('./database');

function verify(menuItem) {
	// name, description, and price must be NOT NULL
	if (menuItem.name === undefined || menuItem.name === null) {
		return new Error('A name must be provided.');
	}
	if (menuItem.name > 100) {
		return new Error('Item name must be less than 100 characters.');
	}
	if (menuItem.description === undefined || menuItem.description === null) {
		return new Error('Description must be provided.');
	}
	if (menuItem.price === undefined || menuItem.price === null) {
		return new Error('Price must be provided.');
	}
	if (menuItem.picture_path > 256) {
		return new Error('Picture path must be less than 256 characters.');
	}

	return null;
}

/**
 * Returns data for all menu items. The callback gets two arguments (err, data).
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
 * Gets the menu_item with the specified id
 *
 * @param orderId the ID of the order to search for
 * @param callback
 *
 */
exports.get = function (orderId, callback) {
	database.query('SELECT * FROM menu_item WHERE order_item_id = ?', [orderId], function (err, rows) {
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
 *
 * @param params the specifications to search by
 * @param callback
 *
 */
exports.search = function (params, callback) {
	database.query('SELECT * FROM menu_item WHERE ?', params, function (err, rows) {
		if (err) {
			callback(err, null);
			return;
		}
		callback(null, rows);
	});
};

/**
 * Inserts a new menu_item. The callback gets two arguments (err, data).
 *
 * @param data the data to be inserted into the menu_item table
 * @param callback
 */
exports.add = function (data, callback) {
	var err = verify(data);
	if (err) {
		callback(err);
		return;
	}

	// If data.picturePath is undefined set to null
	data.picture_path = data.picture_path === undefined ? null : data.picture_path;

	database.query('INSERT INTO menu_item (subcategory_id, name, description, picture_path, price) ' +
			'VALUES (?, ?, ?, ?, ?)',
		[data.subcategory_id, data.name, data.description, data.picture_path, data.price],
		function (err, result) {
			if (err) {
				callback(err);
				return;
			}

			callback(null, result.insertId);
		});
};
