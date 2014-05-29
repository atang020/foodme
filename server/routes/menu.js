var express = require('express');
var router = express.Router();
var menuItemModel = require('../models/menuItemModel');

router.get('/', function (req, res) {
	var myMenu;
	menuItemModel.getSorted(function (err, orders) {
		if (err) {
			res.send(500, 'error connecting to database');
		}
		myMenu = orders;
		res.render('menu', {user: {name: 'Phillip'}, categories: myMenu});
	});
});

module.exports = router;
