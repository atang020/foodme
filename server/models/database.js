var mysql = require('mysql');
var config = require('../config')();

module.exports = mysql.createPool({
	host: config.database.host,
	user: config.database.user,
	password: config.database.password,
	database: config.database.db
});