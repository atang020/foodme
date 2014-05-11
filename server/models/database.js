var mysql = require('mysql');
var config = require('../config')();

var pool = mysql.createPool({
	host: config.database.host,
	user: config.database.user,
	password: config.database.password,
	database: config.database.db
});

var getConnection = function (callback) {
	pool.getConnection(function (err, connection) {
		callback(err, connection);
	});
};

var query = function (sql, data, callback) {
	getConnection(function (err, connection) {
		if (err) {
			callback(err);
			return;
		}

		connection.query(sql, data, function (err, result) {
			callback(err, result);
			connection.release();
		});
	});
};

exports.getConnection = getConnection;
exports.query = query;