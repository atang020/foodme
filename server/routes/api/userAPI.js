var express = require('express');
var userModel = require('../../models/userModel');

var router = express.Router();

router.get('/', function(req, res){
	userModel.getAll(function (err, orders) {
		if (err) {
			res.send(500);
		}
		res.json(orders);
	});
});

router.get('/:userId', function (req, res) {
	userModel.get(req.params.userId, function (err, ticket) {
		if (err) {
			res.send(500);
		}
		res.json(ticket);
	});
});

router.post('/', function (req, res) {
	userModel.add(req.body, function (err, id) {
		if (err) {
			res.send(500);
		}

		res.send(id.toString());
	});
});

router.put('/', function (req, res) {
	userModel.update(req.body, function (err) {
		if (err) {
			res.send(500);
		} else {
			res.send(200);
		}
	});
});

router.delete('/:id', function (req, res) {
	userModel.remove(req.params.id, function (err) {
		if (err) {
			res.send(500);
		} else {
			res.send(200);
		}
	});
});

module.exports = router;
