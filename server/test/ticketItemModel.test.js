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
	
	var menuItemA = {"subcategory_id":-1,"name":"Seasoned Fries","description":"They taste good","picture_path":"sample.jpg","price":4};
	var menuItemB = {"subcategory_id":-1,"name":"Curly Fries","description":"curly","picture_path":"sample.jpg","price":1};
		
	var ticketItemA = {"ticket_id" : -1, "menu_item_id" : -1, "quantity" : 1, "notes" : "extra tomatoes", "kitchen_status" : 0};
	var ticketItemB = {"ticket_id" : -1, "menu_item_id" : -1, "quantity" : 2, "notes" : "extra mushrooms", "kitchen_status" : 1};
	var ticketItemC = {"ticket_id" : -1, "menu_item_id" : -1, "quantity" : 1, "notes" : "", "kitchen_status" : 2};

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
					menuItemA.subcategory_id = id;
					menuItemB.subcategory_id = id;
					menuItemModel.add(menuItemA, function (err, id) {
						assert.ifError(err);
						ticketItemA.menu_item_id = id;
						ticketItemB.menu_item_id = id;
						menuItemModel.add(menuItemB, function (err, id) {
							assert.ifError(err);
							ticketItemC.menu_item_id = id;
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
		ticketItemModel.add(ticketItemC, function (err, id) {
			assert.ifError(err);

			ticketItemModel.get(id, function (err, result) {
				assert.ifError(err);

				assert.equal(result.ticket_item_id, id);
				checkEqual(result, ticketItemC);
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
	

	it('add() and then remove() via object', function (done) {
		ticketItemModel.add(ticketItemA, function (err, id) {
			assert.ifError(err);
			ticketItemA.ticket_item_id = id;

			ticketItemModel.remove(ticketItemA, function (err) {
				assert.ifError(err);

				ticketItemModel.get(id, function (err, result) {
					assert.ifError(err);
					assert.equal(result, null);
					done();
				});
			});
		});
	});

	it('add() and then update() a ticket item', function (done) {
		//Adding multiple ticket items to ensure no side-effects
		async.waterfall([
			function (callback) {
				//Add ticketItemB, pass along B's id
				ticketItemModel.add(ticketItemB, callback);
			},
			function (bId, callback) {
				ticketItemB.ticket_item_id = bId;

				//Add ticketItemC, pass along C's id
				ticketItemModel.add(ticketItemC, callback);
			},
			//Both ticket items are in the database, update the quantity for B
			function (cId, callback) {
				ticketItemC.ticket_item_id = cId;
				ticketItemB.quantity += 100;

				//Update, pass along nothing
				ticketItemModel.update(ticketItemB, callback);
			},
			//Ensure the update worked
			function (callback) {
				ticketItemModel.get(ticketItemB.ticket_item_id, function (err, result) {
					assert.ifError(err);
					checkEqual(ticketItemB, result);
					
					callback(null); //Pass along nothing
				});
			},
			//Update C
			function (callback) {
				ticketItemC.kitchen_status += 1;
				ticketItemC.notes = "test";

				//Update, pass along nothing
				ticketItemModel.update(ticketItemC, callback);
			},
			//Ensure that the update worked
			function (callback) {
				ticketItemModel.get(ticketItemC.ticket_item_id, function (err, result) {
					assert.ifError(err);

					checkEqual(result, ticketItemC);
					callback(null);
				});
			}
		], function (err, result) {
			assert.ifError(err);
			done();
		});
	});
});