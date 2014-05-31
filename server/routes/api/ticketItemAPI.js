var express = require('express');
var ticketItemModel = require('../../models/ticketItemModel');
var routeHelper = require('../routeHelper');

var router = express.Router();

router.get('/', function(req, res){
	ticketItemModel.getAll(function (err, orders) {
		if (err) {
			return routeHelper.jsonError(res, err);
		}
		res.json(orders);
	});
});

router.get('/:ticketItemId', function (req, res) {
	ticketItemModel.get(req.params.ticketItemId, function (err, ticket) {
		if (err) {
			return routeHelper.jsonError(res, err);
		}
		res.json(ticket);
	});
});

router.post('/', function (req, res) {
	ticketItemModel.add(req.body, function (err, id) {
		if (err) {
			return routeHelper.jsonError(res, err);
		}

		res.send(id.toString());
	});
});

router.put('/', function (req, res) {
	ticketItemModel.update(req.body, function (err) {
		if (err) {
			return routeHelper.jsonError(res, err);
		}

		res.send(200);
	});
});

router.delete('/:id', function (req, res) {
	ticketItemModel.remove(req.params.id, function (err) {
		if (err) {
			return routeHelper.jsonError(res, err);
		}

		res.send(200);
	});
});

module.exports = router;
