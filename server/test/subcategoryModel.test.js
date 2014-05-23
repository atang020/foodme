process.env.NODE_ENV = 'test';

var assert = require('assert');
var subcategoryModel = require('../models/subcategoryModel');
var database = require('../models/database');
var async = require('async');

describe('Subcategory model', function () {
	var subcategoryA = {name: meat, category: entrees},
        subcategoryB = {name: beerCheeseDonuts, category: desserts},
        subcategoryC = {name: salads, category: appetizers};

	function checkEqual(a, b) {
		assert.equal(a.name, b.name);
		assert.equal(a.category, b.category);
	}

	beforeEach(function (done) {
		database.query('SET FOREIGN_KEY_CHECKS = 0;TRUNCATE TABLE subcategory;SET FOREIGN_KEY_CHECKS = 1;', function (err) {
			assert.ifError(err);
			done();
		});
	});

	it('add() a subcategory and then get() it by primary id', function (done) {
		subcategoryModel.add(subcategoryA, function (err, id) {
			assert.ifError(err);

			subcategoryModel.get(id, function (err, result) {
				assert.ifError(err);

				assert.equal(result.subcategory_id, id);
				checkEqual(result, subcategoryA);
				done();
			});
		});
	});

	it('get() returns null when there is no data', function (done) {
		subcategoryModel.get(1, function (err, result) {
			assert.ifError(err);
			assert.equal(result, null);
			done();
		})
	});

	it('getAll() returns [] when there is no data', function (done) {
		subcategoryModel.getAll(function (err, result) {
			assert.ifError(err);
			assert.equal(result.length, 0);
			done();
		});
	});

	it('add() multiple subcategorys and then getAll() to retrieve them', function (done) {
		async.series([
			function (callback) {
				subcategoryModel.add(subcategoryA, callback);
			},
			function (callback) {
			    subcategoryModel.add(subcategoryB, callback);
			},
			function (callback) {
				subcategoryModel.add(subcategoryC, callback);
			}
		],
			function (err, idList) {
				assert.ifError(err);

				subcategoryModel.getAll(function (err, subcategories) {
					assert.ifError(err);

					assert.equal(subcategories[0].subcategory_id, idList[0]);
					checkEqual(subcategories[0], subcategoryA);

					assert.equal(subcategories[1].subcategory_id, idList[1]);
					checkEqual(subcategories[1], subcategoryB);

					assert.equal(subcategories[2].subcategory_id, idList[2]);
					checkEqual(subcategories[2], subcategoryC);

					done();
				});
			}
		);
	});

	it('add() and then remove() via id', function (done) {
		subcategoryModel.add(subcategoryA, function (err, id) {
			assert.ifError(err);

			subcategoryModel.remove(id, function (err) {
				assert.ifError(err);

				subcategoryModel.get(id, function (err, result) {
					assert.ifError(err);
					assert.equal(result, null);
					done();
				});
			});
		});
	});

	it('add() and then remove() via object', function (done) {
		subcategoryModel.add(subcategoryA, function (err, id) {
			assert.ifError(err);
			subcategoryA.subcategory_id = id;

			subcategoryModel.remove(subcategoryA, function (err) {
				assert.ifError(err);

				subcategoryModel.get(id, function (err, result) {
					assert.ifError(err);
					assert.equal(result, null);
					done();
				});
			});
		});
	});

	it('add() and then update() a subcategory', function (done) {
		//Adding multiple subcategorys to ensure no side-effects
		async.waterfall([
			function (callback) {
				//Add subcategoryB, pass along B's id
				subcategoryModel.add(subcategoryB, callback);
			},
			function (bId, callback) {
				subcategoryB.subcategory_id = bId;

				//Add subcategoryC, pass along C's id
				subcategoryModel.add(subcategoryC, callback);
			},
			//Both subcategorys are in the database, update the table_number for B
			function (cId, callback) {
				subcategoryC.subcategory_id = cId;
				subcategoryB.table_number += 100;

				//Update, pass along nothing
				subcategoryModel.update(subcategoryB, callback);
			},
			//Ensure the update worked
			function (callback) {
				subcategoryModel.get(subcategoryB.subcategory_id, function (err, result) {
					assert.ifError(err);
					checkEqual(subcategoryB, result);
					callback(null); //Pass along nothing
				});
			},
			//Update C
			function (callback) {
				subcategoryC.table_number += 100;
				subcategoryC.checked_out = 1;
				subcategoryC.call_waiter_status = 1;
				subcategoryC.subcategory_date = new Date('2014-05-23 17:31:10');

				//Update, pass along nothing
				subcategoryModel.update(subcategoryC, callback);
			},
			//Ensure that the update worked
			function (callback) {
				subcategoryModel.get(subcategoryC.subcategory_id, function (err, result) {
					assert.ifError(err);

					checkEqual(result, subcategoryC);
					callback(null);
				});
			}
		], function (err, result) {
			assert.ifError(err);
			done();
		});
	});
});
