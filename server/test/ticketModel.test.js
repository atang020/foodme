process.env.NODE_ENV = 'test';

var assert = require('assert');
var ticketModel = require('../models/ticketModel');
var database = require('../models/database');
var async = require('async');

describe('Ticket model', function () {
	var ticketA = {table_number: 10, ticket_date: new Date('2014-05-20 10:03:39'), checked_out: 0, call_waiter_status: 0},
		ticketB = {table_number: 20, ticket_date: new Date('2014-05-21 15:34:10'), checked_out: 0, call_waiter_status: 0},
		ticketC = {table_number: 30, ticket_date: new Date('2014-05-22 18:55:29'), checked_out: 0, call_waiter_status: 0};

	function checkEqual(a, b) {
		assert.equal(a.table_number, b.table_number);
		assert.equal(a.ticket_date.getTime(), b.ticket_date.getTime());
		assert.equal(a.checked_out, b.checked_out);
		assert.equal(a.call_waiter_status, b.call_waiter_status);
	}

	beforeEach(function (done) {
		database.query('SET FOREIGN_KEY_CHECKS = 0;TRUNCATE TABLE ticket;SET FOREIGN_KEY_CHECKS = 1;', function (err) {
			assert.ifError(err);
			done();
		});
	});

	it('add() a ticket and then get() it by primary id', function (done) {
		ticketModel.add(ticketA, function (err, id) {
			assert.ifError(err);

			ticketModel.get(id, function (err, result) {
				assert.ifError(err);

				assert.equal(result.ticket_id, id);
				checkEqual(result, ticketA);
				done();
			});
		});
	});

	it('get() returns null when there is no data', function (done) {
		ticketModel.get(1, function (err, result) {
			assert.ifError(err);
			assert.equal(result, null);
			done();
		})
	});

	it('getAll() returns [] when there is no data', function (done) {
		ticketModel.getAll(function (err, result) {
			assert.ifError(err);
			assert.equal(result.length, 0);
			done();
		});
	});

	it('add() multiple tickets and then getAll() to retrieve them', function (done) {
		async.series([
			function (callback) {
				ticketModel.add(ticketA, callback);
			},
			function (callback) {
				ticketModel.add(ticketB, callback);
			},
			function (callback) {
				ticketModel.add(ticketC, callback);
			}
		],
			function (err, idList) {
				assert.ifError(err);

				ticketModel.getAll(function (err, tickets) {
					assert.ifError(err);

					assert.equal(tickets[0].ticket_id, idList[0]);
					checkEqual(tickets[0], ticketA);

					assert.equal(tickets[1].ticket_id, idList[1]);
					checkEqual(tickets[1], ticketB);

					assert.equal(tickets[2].ticket_id, idList[2]);
					checkEqual(tickets[2], ticketC);

					done();
				});
			}
		);
	});

	it('add() and then remove() via id', function (done) {
		ticketModel.add(ticketA, function (err, id) {
			assert.ifError(err);

			ticketModel.remove(id, function (err) {
				assert.ifError(err);

				ticketModel.get(id, function (err, result) {
					assert.ifError(err);
					assert.equal(result, null);
					done();
				});
			});
		});
	});

	it('add() and then remove() via object', function (done) {
		ticketModel.add(ticketA, function (err, id) {
			assert.ifError(err);
			ticketA.ticket_id = id;

			ticketModel.remove(ticketA, function (err) {
				assert.ifError(err);

				ticketModel.get(id, function (err, result) {
					assert.ifError(err);
					assert.equal(result, null);
					done();
				});
			});
		});
	});

	it('add() and then update() a ticket', function (done) {
		//Adding multiple tickets to ensure no side-effects
		async.waterfall([
			function (callback) {
				//Add ticketB, pass along B's id
				ticketModel.add(ticketB, callback);
			},
			function (bId, callback) {
				ticketB.ticket_id = bId;

				//Add ticketC, pass along C's id
				ticketModel.add(ticketC, callback);
			},
			//Both tickets are in the database, update the table_number for B
			function (cId, callback) {
				ticketC.ticket_id = cId;
				ticketB.table_number += 100;

				//Update, pass along nothing
				ticketModel.update(ticketB, callback);
			},
			//Ensure the update worked
			function (callback) {
				ticketModel.get(ticketB.ticket_id, function (err, result) {
					assert.ifError(err);
					checkEqual(ticketB, result);
					callback(null); //Pass along nothing
				});
			},
			//Update C
			function (callback) {
				ticketC.table_number += 100;
				ticketC.checked_out = 1;
				ticketC.call_waiter_status = 1;
				ticketC.ticket_date = new Date('2014-05-23 17:31:10');

				//Update, pass along nothing
				ticketModel.update(ticketC, callback);
			},
			//Ensure that the update worked
			function (callback) {
				ticketModel.get(ticketC.ticket_id, function (err, result) {
					assert.ifError(err);

					checkEqual(result, ticketC);
					callback(null);
				});
			}
		], function (err, result) {
			assert.ifError(err);
			done();
		});
	});
});