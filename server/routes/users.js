var express = require('express');
var router = express.Router();
var usersModel = require('../models/userModel');

/* GET users listing. */
router.get('/', function (req, res) {
	usersModel.getAll(function (err, data) {
		if (err) {
			throw err;
		}
		res.json(data);
	});
});

module.exports = router;