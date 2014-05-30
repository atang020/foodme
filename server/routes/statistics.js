var express = require('express');
var router = express.Router();
routeHelper = require('../routes/routeHelper');
var menuItemModel = require('../models/menuItemModel');

router.get('/', function (req, res) {
	routeHelper.redirectIfLoggedOut(req, res, function(loggedIn) {
		if(loggedIn){
			menuItemModel.getAll(function (err, menu_items) {
				if (err) {
					res.send(500, 'error connecting to database');
				}
				res.render('statistics', {user: {email: req.cookies.email}, items: menu_items});
			});
		}
	});
});

module.exports = router;
