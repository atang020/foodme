var express = require('express');
var subcategoryModel = require('../../models/subcategoryModel');

var router = express.Router();

router.get('/', function(req, res){
	subcategoryModel.getAll(function (err, orders) {
		if (err) {
			res.send(500);
		}
		res.json(orders);
	});
});

router.get('/:subcategoryId', function (req, res) {
	subcategoryModel.get(req.params.subcategoryId, function (err, ticket) {
		if (err) {
			res.send(500);
		}
		res.json(ticket);
	});
});

router.post('/', function (req, res) {
	subcategoryModel.add(req.body, function (err, id) {
		if (err) {
			res.send(500);
		}

		res.send(id.toString());
	});
});

router.put('/', function (req, res) {
	subcategoryModel.update(req.body, function (err) {
		if (err) {
			res.send(500);
		} else {
			res.send(200);
		}
	});
});

router.delete('/:id', function (req, res) {
	subcategoryModel.remove(id, function (err) {
		if (err) {
			res.send(500);
		} else {
			res.send(200);
		}
	});
});

module.exports = router;
