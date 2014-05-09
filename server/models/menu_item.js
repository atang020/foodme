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
*
* @param orderId the ID of the order to search for
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
*
* @param params the specifications to search by
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

/**
 * Inserts a new menu_item. The callback gets two arguments (err, data).
 *
 * @param data the data to be inserted into the menu_item table
 * @param callback
 */
exports.add = function (data, callback) {
    // name, description, and price must be NOT NULL
    if (data.name === undefined || data.name === null) {
        callback(new Error("Name must be defined."));
        return;
    }
    if (data.name > 100) {
        callback(new Error("Name must be less than 100 characters."));
        return;
    }
    if (data.description === undefined || data.description === null) {
        callback(new Error("Description must be defined."));
        return;
    }
    if (data.price === undefined || data.price === null) {
        callback(new Error("Price must be defined."));
        return;
    }
    if (data.picturePath > 256) {
        callback(new Error("Picture path must be less than 256 characters."));
        return;
    }

    // If data.picturePath is undefined set to null
    data.picturePath = data.picturePath === undefined ? null : data.picturePath;

    database.query('INSERT INTO menu_item (subcategory_id, name, description, picture_path, price) ' +
                    'VALUES (?, ?, ?, ?, ?)',
                    [data.subcategoryId, data.name, data.description, data.picturePath, data.price],
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
