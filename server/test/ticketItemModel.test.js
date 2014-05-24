process.env.NODE_ENV = 'test';

var assert = require('assert');
var ticketModel = require('../models/ticketModel');
var ticketItemModel = require('../models/ticketItemModel');
var subcategoryModel = require('../models/subcategoryModel');
var menuItemModel = require('../models/menuItemModel');
var database = require('../models/database');
var async = require('async');

describe('Ticket item model', function () {
	var ticketA = {table_number: 10, ticket_date: new Date('2014-05-20 10:03:39'), checked_out: 0, call_waiter_status: 0};
		//ticketB = {table_number: 20, ticket_date: new Date('2014-05-21 15:34:10'), checked_out: 0, call_waiter_status: 0},
		//ticketC = {table_number: 30, ticket_date: new Date('2014-05-22 18:55:29'), checked_out: 0, call_waiter_status: 0};
		
	var subcategoryA = {"name":"Fries","category":10};
	
	var menuItemA = {"subcategory_id":1,"name":"Seasoned Fries","description":"They taste good","picture_path":"sample.jpg","price":4};
	var menuItemB = {"subcategory_id":1,"name":"Curly Fries","description":"curly","picture_path":"sample.jpg","price":1};
		
	var ticketItemA = {"ticket_id" : -1, "menu_item_id" : 2, "quantity" : 1, "notes" : "extra tomatoes", "kitchen_status" : 0};
	var ticketItemB = {"ticket_id" : -1, "menu_item_id" : 2, "quantity" : 2, "notes" : "extra mushrooms", "kitchen_status" : 1};
	var ticketItemC = {"ticket_id" : -1, "menu_item_id" : 2, "quantity" : 1, "notes" : "", "kitchen_status" : 2};

	function checkEqual(a, b) {
		assert.equal(a.ticket_id, b.ticket_id);
		assert.equal(a.menu_item_id, b.menu_item_id);
		assert.equal(a.quantity, b.quantity);
		assert.equal(a.notes, b.notes);
		assert.equal(a.kitchen_status, b.kitchen_status);
	}
	
	before(function (done) {
		database.query('SET FOREIGN_KEY_CHECKS = 0;TRUNCATE TABLE ticket;TRUNCATE TABLE menu_item;TRUNCATE TABLE subcategory;SET FOREIGN_KEY_CHECKS = 1;', function (err) {
			assert.ifError(err);
			ticketModel.add(ticketA, function (err, id) {
				assert.ifError(err);
				ticketItemA.ticket_id = id;
				ticketItemB.ticket_id = id;
				ticketItemC.ticket_id = id;
				subcategoryModel.add(subcategoryA, function (err, id) {
					assert.ifError(err);
					menuItemModel.add(menuItemA, function (err, id) {
						assert.ifError(err);
						menuItemModel.add(menuItemB, function (err, id) {
							assert.ifError(err);
							done();
						});
					});
				});
			});
		});
	});

	beforeEach(function (done) {
		database.query('SET FOREIGN_KEY_CHECKS = 0;TRUNCATE TABLE ticket_item;SET FOREIGN_KEY_CHECKS = 1;', function (err) {
			assert.ifError(err);
			done();
		});
	});

	it('add() a ticket item and then get it', function (done) {
		ticketItemModel.add(ticketItemA, function (err, id) {
			assert.ifError(err);

			ticketItemModel.get(id, function (err, result) {
				assert.ifError(err);

				assert.equal(result.ticket_item_id, id);
				checkEqual(result, ticketItemA);
				done();
			});
		});
	});

	it('get() returns null when there is no data', function (done) {
		ticketItemModel.get(1, function (err, result) {
			assert.ifError(err);
			assert.equal(result, null);
			done();
		})
	});

	it('getAll() returns [] when there is no data', function (done) {
		ticketItemModel.getAll(function (err, result) {
			assert.ifError(err);
			assert.equal(result.length, 0);
			done();
		});
	});
	
	
	/*
	it('add() multiple ticket items and then getAll() to retrieve them', function (done) {
		async.series([
			function (callback) {
				ticketItemModel.add(ticketItemA, callback);
			},
			function (callback) {
				ticketItemModel.add(ticketItemB, callback);
			},
			function (callback) {
				ticketItemModel.add(ticketItemC, callback);
			}
		],
			function (err, idList) {
				assert.ifError(err);

				ticketItemModel.getAll(function (err, ticketItems) {
					assert.ifError(err);

					assert.equal(ticketItems[0].ticket_item_id, idList[0]);
					checkEqual(ticketItems[0], ticketItemA);

					assert.equal(ticketItems[1].ticket_item_id, idList[1]);
					checkEqual(ticketItems[1], ticketItemB);

					assert.equal(ticketItems[2].ticket_item_id, idList[2]);
					checkEqual(ticketItems[2], ticketItemC);

					done();
				});
			}
		);
	});
	*/
	

	it('add() and then remove() via id', function (done) {
		ticketItemModel.add(ticketItemB, function (err, id) {
			assert.ifError(err);

			ticketItemModel.remove(id, function (err) {
				assert.ifError(err);

				ticketItemModel.get(id, function (err, result) {
					assert.ifError(err);
					assert.equal(result, null);
					done();
				});
			});
		});
	});
	/*

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
	*/
});