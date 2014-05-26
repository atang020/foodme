process.env.NODE_ENV = 'test';

var assert = require('assert');
var subcategoryModel = require('../models/subcategoryModel');
var database = require('../models/database');
var async = require('async');

describe('Subcategory model', function () {
	var subcategoryA = {name: 'meat', category: 30},
        subcategoryB = {name: 'beerCheeseDonuts', category: 40},
        subcategoryC = {name: 'salads', category: 20};

	function checkEqual(a, b) {
		assert.equal(a.name, b.name);
		assert.equal(a.category, b.category);
	}

	beforeEach(function (done) {
	    database.query('SET FOREIGN_KEY_CHECKS = 0;TRUNCATE TABLE subcategory;SET FOREIGN_KEY_CHECKS = 1', null, function (err) {
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

	it('add() multiple subcategories and then getAll() to retrieve them', function (done) {
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
});
