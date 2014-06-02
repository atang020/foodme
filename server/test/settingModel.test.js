process.env.NODE_ENV = 'test';

var assert = require('assert');
var settingModel = require('../models/settingModel');
var database = require('../models/database');
var async = require('async');

describe('Setting model', function () {
	/*var settingA = {key: '10', value: '10'};
	var settingB = {key: '11',value: '11'};
	var settingC = {key: '12',value: '12'};
	var settingD = {key: '13',value: '13'};*/

	var settingA = {value: '10'};
	var settingB = {value: '11'};
	var settingC = {value: '12'};
	var settingD = {value: '13'};

	function checkEqual(a, b) {
		//assert.equal(a.key, b.key);
		assert.equal(a.value, b.value);
	}
	it('add() a setting and then get() it by key', function (done) {
		settingModel.add(settingA, function (err, id) {
			assert.ifError(err);

			subcategoryModel.get(key, function (err, result) {
				assert.ifError(err);

				assert.equal(result.subcategory_id, id);
				checkEqual(result, subcategoryA);
				done();
			});
		});
	});

	it('get() returns null when there is no data', function (done) {
		settingModel.get(1, function (err, result) {
			assert.ifError(err);
			assert.equal(result, null);
			done();
		})
	});

	it('getAll() returns [] when there is no data', function (done) {
		settingModel.getAll(function (err, result) {
			assert.ifError(err);
			assert.equal(result.length, 0);
			done();
		});
	});

	it('add() multiple settings and then getAll() to retrieve them', function (done) {
		async.series([
			function (callback) {
				settingModel.add(settingA, callback);
			},
			function (callback) {
			    settingModel.add(settingB, callback);
			},
			function (callback) {
				settingModel.add(settingC, callback);
			}
		],
			function (err, idList) {
				assert.ifError(err);

				settingModel.getAll(function (err, subcategories) {
					assert.ifError(err);

					assert.equal(settings[0].key, idList[0]);
					checkEqual(settings[0], settingA);

					assert.equal(settings[1].key, idList[1]);
					checkEqual(settings[1], settingB);

					assert.equal(settings[2].key, idList[2]);
					checkEqual(settings[2], settingC);

					done();
				});
			}
		);
	});

	it('add() and then remove() via key', function (done) {
		settingModel.add(settingA, function (err, id) {
			assert.ifError(err);

			settingModel.remove(key, function (err) {
				assert.ifError(err);

				settingModel.get(key, function (err, result) {
					assert.ifError(err);
					assert.equal(result, null);
					done();
				});
			});
		});
	});

}