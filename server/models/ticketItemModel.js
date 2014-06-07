var database = require('./database');
var ticketModel = require('./ticketModel');
var menuItemModel = require('./menuItemModel');
var async = require('async');

function verify(ticketItem) {
	// ticket_id, menu_item_id, and quantity must be NOT NULL
	if (ticketItem.quantity === undefined || ticketItem.quantity === null) {
		return new Error('A quantity must be provided.');
	}

	if (ticketItem.ticket_id === undefined || ticketItem.ticket_id === null) {
		return new Error('ticket ID not specified.');
	}

	if (ticketItem.menu_item_id === undefined || ticketItem.menu_item_id === null) {
		return new Error('Menu Item ID not specified.');
	}
	return null;
}

/**
 * Returns data for all ticket items. The callback gets two arguments (err, data).
 *
 * @param callback
 */
exports.getAll = function (callback) {
	database.query('SELECT * FROM ticket_item', null, function (err, rows) {
		if (err) {
			callback(err, null);
			return;
		}
		callback(null, rows);
	});
};

/**
 * Returns data for all active non-delivered orders together with the order's ticket and its menu_item
 *
 * @param callback
 */
exports.getActiveOrders = function (callback) {
	exports.getAllAfter(0, callback);
};

/**
 * Returns all ticket items that come after a certain ticket item
 *
 * @param id the most recent ticket item
 * @param callback (err, data)
 */
exports.getAllAfter = function (id, callback) {
	database.query('SELECT ticket_item_id, ticket_item.ticket_id, quantity, notes, table_number, name FROM ticket_item INNER JOIN menu_item ON ticket_item.menu_item_id = menu_item.menu_item_id INNER JOIN ticket ON ticket_item.ticket_id = ticket.ticket_id WHERE ticket_item_id > ? AND kitchen_status = 0 ORDER BY ticket_item_id ASC;', id, function (err, ticketItems) {
		if (err) {
			return callback(err);
		}
		callback(null, ticketItems);
	});
};

/**
 * Gets the ticket_item with the specified id
 * @param ticketId
 * @param callback
 *
 */
exports.get = function (ticketId, callback) {
	database.query('SELECT * FROM ticket_item WHERE ticket_item_id = ?', [ticketId], function (err, rows) {
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
 * Gets the ticket_items that match the given parameters
 * @param params
 * @param callback
 *
 */
exports.search = function (params, callback) {
	database.query('SELECT * FROM ticket_item WHERE ?', params, function (err, rows) {
		if (err) {
			callback(err, null);
			return;
		}
		callback(null, rows);
	});
};

/**
 * Inserts a new ticket_item. The callback gets two arguments (err, data).
 *
 * @param ticketItem the data to be inserted into the ticket_item table
 * @param callback
 */
exports.add = function (ticketItem, callback) {
	var err = verify(ticketItem);
	if (err) {
		return callback(err);
	}


	// if kitchenStatus is undefined, set to 0
	//TODO: WE NEED TO DECIDE WHAT THE DIFFERENT KITCHEN STATUS VALUES MEAN
	ticketItem.kitchen_status = ticketItem.kitchen_status === undefined ? 0 : ticketItem.kitchen_status;
	ticketItem.notes = ticketItem.notes === undefined ? '' : ticketItem.notes;

	database.query('INSERT INTO ticket_item (ticket_id, menu_item_id, quantity, notes, kitchen_status)' +
			'VALUES (?, ?, ?, ?, ?)', [ticketItem.ticket_id, ticketItem.menu_item_id, ticketItem.quantity, ticketItem.notes, ticketItem.kitchen_status],
		function (err, result) {
			if (err) {
				return callback(err);
			}

			callback(null, result.insertId);
		});
};

exports.update = function (ticketItem, callback) {
	var id = ticketItem.ticket_item_id;
	delete ticketItem.ticket_item_id;

	database.query('UPDATE ticket_item SET? WHERE ticket_item_id = ' + id, ticketItem, callback);
};

/**
 * Deletes an ticket_item. The callback gets two arguments (err, data).
 *
 * @param ticketItem the data to be deleted from the ticket_item table
 * @param callback
 */
exports.remove = function (ticketItem, callback) {
	var id = null;

	if (typeof ticketItem === 'object') {
		id = ticketItem.ticket_item_id;
	} else {
		id = ticketItem;
	}

	if (id === null) {
		return callback(new Error('Invalid ticket item: no id present'));
	}

	database.query('DELETE FROM ticket_item WHERE ticket_item_id = ?', id, callback);
};
