var express = require('express');
var router = express.Router();
var ticketItemModel = require('../models/ticketItemModel');

router.get('/', function (req, res) {

	ticketItemModel.getActiveOrders(function (err, orders) {
		if (err) {
			res.send(500,'error connecting to database');
		}
		res.render('orders', {user: {name: 'Phillip'}, ticket_items: orders});
	});
});

router.get('/active', function (req, res) {
	res.send('Welcome to the active orders page');
});

router.get('/archived', function (req, res) {
	res.render('index', {title: 'Cpt', fubar: 'Private'});
});

module.exports = router;
