var mysql = require('mysql');
var config = require('../config')();

var pool = mysql.createPool({
	host: config.database.host,
	user: config.database.user,
	password: config.database.password,
	database: config.database.db,
	multipleStatements: config.database.multipleStatements || false
});

var getConnection = function (callback) {
	pool.getConnection(function (err, connection) {
		return callback(err, connection);
	});
};

var query = function (sql, data, callback) {
	var dataGiven = arguments.length !== 2;

	getConnection(function (err, connection) {
		if (err) {
			return callback(err);
		}

		if (!dataGiven) {
			connection.query(sql, function (err, result) {
				callback = data;
				callback(err, result);
				connection.release();
			});
		}
		else {
			connection.query(sql, data, function (err, result) {
				callback(err, result);
				connection.release();
			});
		}
	});
};

var truncate = function (password, callback) {
	if (password === 'do not use in production') {
		query('SET FOREIGN_KEY_CHECKS = 0;TRUNCATE `menu_item`;TRUNCATE `ticket`;TRUNCATE `ticket_item`;TRUNCATE `review`;TRUNCATE `subcategory`;TRUNCATE `user`;SET FOREIGN_KEY_CHECKS = 1;', null, function (err) {
			callback(err);
		});
	} else {
		callback(null);
	}
};

exports.getConnection = getConnection;
exports.query = query;
exports.truncate = truncate;