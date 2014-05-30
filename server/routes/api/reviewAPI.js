var express = require('express');
var reviewModel = require('../../models/reviewModel');

var router = express.Router();

router.get('/', function(req, res){
	reviewModel.getAll(function (err, orders) {
		if (err) {
			throw err;
		}
		res.json(orders);
	});
});

router.get('/:reviewId', function (req, res) {
	reviewModel.get(req.params.reviewId, function (err, ticket) {
		if (err) {
			throw err;
		}
		res.json(ticket);
	});
});

router.post('/', function (req, res) {
	reviewModel.add(req.body, function (err, id) {
		if (err) {
			throw err;
		}

		res.send(id.toString());
	});
});

router.put('/', function (req, res) {
	reviewModel.update(req.body, function (err) {
		if (err) {
			throw err;
		} else {
			res.send(200);
		}
	});
});

router.delete('/:id', function (req, res) {
	reviewModel.remove(req.params.id, function (err) {
		if (err) {
			throw err;
		} else {
			res.send(200);
		}
	});
});

module.exports = router;
