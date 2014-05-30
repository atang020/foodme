var express = require('express');
var router = express.Router();
var menuItemModel = require('../models/menuItemModel');
routeHelper = require('../routes/routeHelper');

router.get('/', function (req, res) {
	routeHelper.redirectIfLoggedOut(req, res, function (loggedIn) {
		if (loggedIn) {
			var myMenu;
			menuItemModel.getSorted(function (err, orders) {
				if (err) {
					res.send(500, 'error connecting to database');
				}
				myMenu = orders;
				res.render('menu', {user: {email: req.cookies.email}, categories: myMenu});
			});
		}
	});
});


module.exports = router;
