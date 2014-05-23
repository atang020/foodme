var express = require('express');
var router = express.Router();

router.get('/', function (req, res) {
	res.render('orders', {orders: [
		{id: 1, table: 5, time: 26, item: 'Ritz Crackers and Salami', status: 'undelivered'},
		{id: 2, table: 2, time: 18, item: 'Cheerios', status: 'undelivered'},
		{id: 3, table: 7, time: 6, item: 'Coffee', notes: 'lol', status: 'undelivered'},
		{id: 4, table: 15, time: 5, item: 'Small Fries', status: 'undelivered'}
	], user: {name: 'Phillip'}});
});

router.get('/active', function (req, res) {
	res.send('Welcome to the active orders page');
});

router.get('/archived', function (req, res) {
	res.render('index', {title: 'Cpt', fubar: 'Private'});
});

module.exports = router;
