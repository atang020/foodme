var database = require('./database');

function verify(ticket) {
	// table_number must be NOT NULL
	if (ticket.table_number === undefined || ticket.table_number === null) {
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
	database.query('SELECT * FROM ticket', function (err, rows) {
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
 * @param ticketId
 * @param callback
 */
exports.get = function (ticketId, callback) {
	database.query('SELECT * FROM ticket WHERE ticket_id = ?', [ticketId], function (err, rows) {
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
 * Inserts a new ticket into the database. The callback gets two arguments (err, data).
 *
 * @param ticket the data to be inserted into the ticket table
 * @param callback
 */
exports.add = function (ticket, callback) {
	var err = verify(ticket);
	if (err) {
		callback(err);
		return;
	}


	// if checked_out or call_waiter_status are undefined, set to 0
	ticket.checked_out = ticket.checked_out === undefined ? 0 : ticket.checked_out;
	ticket.call_waiter_status = ticket.call_waiter_status === undefined ? 0 : ticket.call_waiter_status;
	ticket.ticket_date = ticket.ticket_date === undefined ? null : ticket.ticket_date;

	database.query('INSERT INTO ticket (table_number, ticket_date, checked_out, call_waiter_status)' +
			'VALUES (?, ?, ?, ?)', [ticket.table_number, ticket.ticket_date, ticket.checked_out, ticket.call_waiter_status],
		function (err, result) {
			if (err) {
				callback(err);
				return;
			}

			callback(null, result.insertId);
		});
};

exports.update = function (ticket, callback) {
	var err = verify(ticket);
	if (err) {
		callback(err);
		return;
	}

	database.query('UPDATE ticket SET table_number = ?, ticket_date = ?, checked_out = ?, call_waiter_status = ? WHERE ticket_id = ?',
		[ticket.table_number, ticket.ticket_date, ticket.checked_out, ticket.call_waiter_status, ticket.ticket_id], function (err) {

			if (err) {
				callback(err);
				return;
			}
			callback(null);
		});
};

/**
 * Deletes an ticket. The callback gets two arguments (err, data).
 *
 * @param ticket the data to be deleted from the ticket table
 * @param callback
 */
exports.remove = function (ticket, callback) {
	var id = null;

	if (typeof ticket === 'object') {
		id = ticket.ticket_id;
	} else {
		id = ticket;
	}

	if (id === null) {
		callback(new Error('Invalid ticket: no id present'));
		return;
	}

	database.query('DELETE FROM ticket WHERE ticket_id = ?', id, function (err) {
		if (err) {
			callback(err);
			return;
		}
		callback(null);
	});
};
