process.env.NODE_ENV = 'test';

var assert = require('assert');
var userModel = require('../models/userModel');
var database = require('../models/database');
var async = require('async');

describe('User model', function () {
	var userA = {password: 'userAPassword', email: 'userA@example.com', phone: '447-239-3923'},
		userB = {password: 'userBPassword', email: 'userB@example.com', phone: '585-593-5921'},
		userC = {password: 'userCPassword', email: 'userC@example.com', phone: '939-383-8512'};

	beforeEach(function (done) {
		//Delete all users from the DB
		database.query('TRUNCATE TABLE user', null, function (err) {
			assert.ifError(err);
			done();
		});
	});

	it('add() and get()', function (done) {
		userModel.add(userA, function (err, id) {
			assert.ifError(err);

			userModel.get(id, function (err, result) {
				assert.ifError(err);

				assert.equal(result.user_id, id);
				assert.equal(result.password, userA.password);
				assert.equal(result.email, userA.email);
				assert.equal(result.phone, userA.phone);
				done();
			});
		});
	});

	it('get() returns null when there is no data', function (done) {
		userModel.get(1, function (err, result) {
			assert.ifError(err);
			assert.equal(result, null);
			done();
		});
	});

	it('getAll() returns [] when there is no data', function (done) {
		userModel.getAll(function (err, result) {
			assert.ifError(err);
			assert.equal(result.length, 0);
			done();
		});
	});

	it('add() and getAll()', function (done) {
		async.series([
			function (callback) {
				userModel.add(userA, callback);
			},
			function (callback) {
				userModel.add(userB, callback);
			},
			function (callback) {
				userModel.add(userC, callback);
			}
		],
		function (err, results) {
			assert.ifError(err);

			userModel.getAll(function (err, users) {
				assert.ifError(err);

				//userA
				assert.equal(users[0].user_id, results[0]);
				assert.equal(users[0].password, userA.password);
				assert.equal(users[0].email, userA.email);
				assert.equal(users[0].phone, userA.phone);

				//userB
				assert.equal(users[1].user_id, results[1]);
				assert.equal(users[1].password, userB.password);
				assert.equal(users[1].email, userB.email);
				assert.equal(users[1].phone, userB.phone);

				//userC
				assert.equal(users[2].user_id, results[2]);
				assert.equal(users[2].password, userC.password);
				assert.equal(users[2].email, userC.email);
				assert.equal(users[2].phone, userC.phone);

				done();
			});
		});
	});

	it('add() and update()', function (done) {
		userModel.add(userA, function (err, id) {
			assert.ifError(err);

			userA.user_id = id;
			userA.password = 'userAPassword2';
			userA.email = 'userA2@example.com';
			userA.phone = '593-349-4930';

			userModel.update(userA, function (err) {
				assert.ifError(err);

				//Get the user now
				userModel.get(userA.user_id, function (err, userA2) {
					assert.ifError(err);
					assert.equal(userA2.user_id, userA.user_id);
					assert.equal(userA2.password, userA.password);
					assert.equal(userA2.email, userA.email);
					assert.equal(userA2.phone, userA.phone);
					done();
				});
			});
		});
	});

	it('add() and then remove()', function (done) {
		userModel.add(userB, function (err, id) {
			assert.ifError(err);
			userB.user_id = id;
			userModel.remove(userB, function (err) {
				assert.ifError(err);

				//Now try getting the user
				userModel.get(id, function (err, result) {
					assert.ifError(err);
					assert.equal(result, null);
					done();
				});
			});
		});
	});
});