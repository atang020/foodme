var express = require('express');
var router = express.Router();
var ticketItemModel = require('../models/ticketItemModel');
routeHelper = require('../routes/routeHelper');

router.get('/', function (req, res) {
	routeHelper.redirectIfLoggedOut(req, res, function (loggedIn) {
		if (loggedIn) {
			ticketItemModel.getActiveOrders(function (err, orders) {
				if (err) {
					res.send(500, 'error connecting to database');
				}
				res.render('orders', {user: {email: req.cookies.email}, ticket_items: orders});
			});
		}
	});
});

router.get('/active', function (req, res) {
	res.send('Welcome to the active orders page');
});

router.get('/archived', function (req, res) {
	res.render('index', {title: 'Cpt', fubar: 'Private'});
});

module.exports = router;
