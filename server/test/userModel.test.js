process.env.NODE_ENV = 'test';

var assert = require('assert');
var userModel = require('../models/userModel');

describe('User model', function () {
	it('Adding user and retrieving their info', function (done) {
		userModel.add({password: 'testPassword', email: 'test@example.com', phone: '123-456-7890'}, function (err, id) {
			assert.ifError(err);

			userModel.get(id, function (err, result) {
				assert.ifError(err);

				assert.equal(result.user_id, id);
				assert.equal(result.password, 'testPassword');
				assert.equal(result.email, 'test@example.com');
				assert.equal(result.phone, '123-456-7890');
				done();
			});
		});
	});
});