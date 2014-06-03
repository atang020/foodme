var express = require('express');
var router = express.Router();
var ticketItemModel = require('../models/ticketItemModel');
var ticketModel = require('../models/ticketModel');
routeHelper = require('../routes/routeHelper');

router.get('/', function (req, res) {
	routeHelper.redirectIfLoggedOut(req, res, function (loggedIn) {
		if (loggedIn) {
			//ticketModel.search({call_waiter_status: 1}, function(err, tickets) {
			ticketModel.getAll(function(err, tickets) {
				tickets.push({table: 12, ticket_id: 1, call_waiter_status: 0});
				tickets.push({table: 2, ticket_id: 2, call_waiter_status: 0});
				if(err)
					res.send(500, 'database problem');
				ticketItemModel.getActiveOrders(function (err, orders) {
					if (err) {
						res.send(500, 'error connecting to database');
					}
					res.render('orders', {user: {email: req.cookies.email}, ticket_items: orders, tickets: tickets});
				});
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
