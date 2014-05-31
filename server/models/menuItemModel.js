var database = require('./database');
var async = require('async');
var subcategoryModel = require('./subcategoryModel');
var ticketItemModel = require('./ticketItemModel');

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
	database.query('SELECT * FROM menu_item', null, function (err, rows) {
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
	database.query('SELECT * FROM menu_item WHERE menu_item_id = ?', [orderId], function (err, rows) {
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
 * Returns data for all menu items, with data for how they have been ordered
 * TODO implement the cutoffDate filtering
 *
 * @param cutoffDate the earliest date of the order data we want to include
 * @param sortBy the variable to sort by e.g. "profit" or "numberOrdered"
 * @param ascending true for ascending order, false for descending
 * @param callback
 *
 */
exports.getAllWithStatistics = function (cutoffDate, sortBy, ascending, callback) {
	exports.getAll(function (err, menu_items) {
		if (err) {
			return callback(err);
		}

		async.eachLimit(menu_items, 5,
			function (menu_item, asyncCallback) {
				ticketItemModel.search({menu_item_id: menu_item.menu_item_id}, function (err, ticketItems) {
					if (err) {
						return asyncCallback(err);
					}
					var numberOrdered = 0;
					for(var i = 0; i < ticketItems.length; i++) {
						numberOrdered += ticketItems[i].quantity;
					}
					menu_item.numberOrdered = numberOrdered;
					//it sure would be nice if the price per item were stored in the ticket table so if it
					//changed the historical data would remain unchanged...
					menu_item.profit = numberOrdered * menu_item.price;
					//TODO also get rating information
					return asyncCallback(null);
				});
			},
			function (err) {
				if (err) {
					return callback(err);
				}
				//this might be slow if there's lots of menu items
				if(!ascending)
					menu_items.sort(function(a,b) { return parseFloat(b[sortBy]) - parseFloat(a[sortBy])} );
				else
					menu_items.sort(function(a,b) { return parseFloat(a[sortBy]) - parseFloat(b[sortBy])} );
				return callback(null, menu_items);
			});
	});
};

exports.getSorted = function (callback) {
	var results = {'appetizers': {subcategories: [], id: 0}, 'drinks': {subcategories: [], id: 10}, 'entrees': {subcategories: [], id: 20}, 'desserts': {subcategories: [], id: 30}};

	subcategoryModel.getAll(function (err, subcategories) {
		if (err) {
			return callback(err);
		}

		async.eachLimit(subcategories, 5,
			function (subcategory, asyncCallback) {
				exports.search({subcategory_id: subcategory.subcategory_id}, function (err, menuItems) {
					if (err) {
						return asyncCallback(err);
					}
					subcategory.items = menuItems;
					results[subcategoryModel.categories[subcategory.category.toString()]].subcategories.push(subcategory);
					return asyncCallback(null);
				});
			},
			function (err) {
				if (err) {
					return callback(err);
				}
				return callback(null, results);
			});
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
 * @param menuItem the data to be inserted into the menu_item table
 * @param callback
 */
exports.add = function (menuItem, callback) {
	var err = verify(menuItem);
	if (err) {

		callback(err);
		return;
	}


	// If data.picturePath is undefined set to null
	menuItem.picture_path = menuItem.picture_path === undefined ? null : menuItem.picture_path;

	database.query('INSERT INTO menu_item (subcategory_id, name, description, picture_path, price) ' +
			'VALUES (?, ?, ?, ?, ?)',
		[menuItem.subcategory_id, menuItem.name, menuItem.description, menuItem.picture_path, menuItem.price],
		function (err, result) {
			if (err) {
				callback(err);
				return;
			}

			callback(null, result.insertId);
		});
};

exports.update = function (menuItem, callback) {
	var err = verify(menuItem);
	if (err) {
		callback(err);
		return;
	}

	database.query('UPDATE menu_item SET subcategory_id = ?, name = ?, description = ?, picture_path = ?, price = ? WHERE menu_item_id = ?',
		[menuItem.subcategory_id, menuItem.name, menuItem.description, menuItem.picture_path, menuItem.price, menuItem.menu_item_id], function (err) {

			if (err) {
				callback(err);
				return;
			}
			callback(null);
		});
};

exports.updatePhoto = function (menuItemId, photoPath, callback) {
	database.query('UPDATE menu_item SET picture_path = ? WHERE menu_item_id = ?', [photoPath, menuItemId], function (err) {
		if (err) {
			return callback(err);
		}
		return  callback(null, photoPath);
	});
};

/**
 * Deletes a menu_item. The callback gets two arguments (err, data).
 *
 * @param menuItem the data to be deleted from the menu_item table
 * @param callback
 */
exports.remove = function (menuItem, callback) {
	var id = null;

	if (typeof menuItem === 'object') {
		id = menuItem.menu_item_id;
	} else {
		id = menuItem;
	}

	if (id === null) {
		callback(new Error('Invalid menu item: no id present'));
		return;
	}

	database.query('DELETE FROM menu_item WHERE menu_item_id = ?', id, function (err) {
		if (err) {
			callback(err);
			return;
		}
		callback(null);
	});
};

/**
 * Deletes all the menu_items in a subcategory. The callback gets two arguments (err, data).
 *
 * @param subcategory subcategory to delete the items from
 * @param callback
 */
exports.removeAllInSubcategory = function (subcategory, callback) {
	var id = null;

	if (typeof subcategory === 'object') {
		id = subcategory.subcategory_id;
	} else {
		id = subcategory;
	}

	if (id === null) {
		callback(new Error('Invalid subcategory: no id present'));
		return;
	}

	database.query('DELETE FROM menu_item WHERE subcategory_id = ?', id, function (err) {
		if (err) {
			callback(err);
			return;
		}
		callback(null);
	});
};