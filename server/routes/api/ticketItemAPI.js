var express = require('express');
var ticketItemModel = require('../../models/ticketItemModel');

var router = express.Router();

router.get('/', function(req, res){
	ticketItemModel.getAll(function (err, orders) {
		if (err) {
			res.send(500);
		}
		res.json(orders);
	});
});

router.get('/:ticketItemId', function (req, res) {
	ticketItemModel.get(req.params.ticketItemId, function (err, ticket) {
		if (err) {
			res.send(500);
		}
		res.json(ticket);
	});
});

router.post('/', function (req, res) {
	ticketItemModel.add(req.body, function (err, id) {
		if (err) {
			res.send(500);
		}

		res.send(id.toString());
	});
});

router.put('/', function (req, res) {
	ticketItemModel.update(req.body, function (err) {
		if (err) {
			res.send(500);
		} else {
			res.send(200);
		}
	});
});

router.delete('/:id', function (req, res) {
	ticketItemModel.remove(id, function (err) {
		if (err) {
			res.send(500);
		} else {
			res.send(200);
		}
	});
});

module.exports = router;
