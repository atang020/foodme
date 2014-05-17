process.env.NODE_ENV = 'test';

var assert = require('assert');
var database = require('../models/database');
var userModel = require('../models/userModel');

describe('Database helper model', function () {
	it('should truncate the database', function (done) {
		this.timeout(0);
		//Add something to the DB to ensure there is data to erase
		userModel.add({password: '1234', email: 'fj@gj.com', phone: '283-483-3923'}, function (err) {
			assert.ifError(err);

			database.truncate('do not use in production', function (err) {
				assert.ifError(err);
				userModel.getAll(function (err, result) {
					assert.ifError(err);
					assert.equal(result.length, 0);
					done();
				});
			});
		});
	});
});