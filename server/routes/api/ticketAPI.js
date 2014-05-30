var express = require('express');
var ticketModel = require('../../models/ticketModel');

var router = express.Router();

router.get('/', function(req, res){
	ticketModel.getAll(function (err, orders) {
		if (err) {
			throw err;
		}
		res.json(orders);
	});
});

router.get('/:ticketId', function (req, res) {
	ticketModel.get(req.params.ticketId, function (err, ticket) {
		if (err) {
			throw err;
		}
		res.json(ticket);
	});
});

router.post('/', function (req, res) {
	ticketModel.add(req.body, function (err, id) {
		if (err) {
			throw err;
		}

		res.send(id.toString());
	});
});

router.put('/', function (req, res) {
	ticketModel.update(req.body, function (err) {
		if (err) {
			throw err;
		} else {
			res.send(200);
		}
	});
});


router.delete('/:id', function (req, res) {
	ticketModel.remove(req.params.id, function (err) {
		if (err) {
			throw err;
		} else {
			res.send(200);
		}
	});
});

module.exports = router;
