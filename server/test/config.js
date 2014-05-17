process.env.NODE_ENV = 'test';

var assert = require('assert');

describe('Configuration setup', function () {
	it('should load development configurations', function () {
		var config = require('../config')('development');
		assert.equal(config.mode, 'development');
	});
	it('should load production configurations', function () {
		var config = require('../config')('test');
		assert.equal(config.mode, 'test');
	});
	it('should load development configurations', function () {
		var config = require('../config')('production');
		assert.equal(config.mode, 'production');
	});
});