var express = require('express');
var userModel = require('../../models/userModel');
var routeHelper = require('../routeHelper');

var router = express.Router();

router.get('/', function(req, res){
	userModel.getAll(function (err, orders) {
		if (err) {
			return routeHelper.jsonError(res, err);
		}
		res.json(orders);
	});
});

router.get('/:userId', function (req, res) {
	userModel.get(req.params.userId, function (err, ticket) {
		if (err) {
			return routeHelper.jsonError(res, err);
		}
		res.json(ticket);
	});
});

router.post('/login', function (req, res) {
	routeHelper.isValidUser(req.body.email, req.body.password, function (err, isValid, userData) {
		if (err) {
			return routeHelper.jsonError(res, err);
		}
		if (isValid) {
			res.send(202);
		} else {
			res.send(401);
		}
	})
});

router.post('/', function (req, res) {
	userModel.add(req.body, function (err, id) {
		if (err) {
			return routeHelper.jsonError(res, err);
		}

		res.send(id.toString());
	});
});

router.put('/', function (req, res) {
	userModel.update(req.body, function (err) {
		if (err) {
			return routeHelper.jsonError(res, err);
		}

		res.send(200);
	});
});

router.delete('/:id', function (req, res) {
	userModel.remove(req.params.id, function (err) {
		if (err) {
			return routeHelper.jsonError(res, err);
		}

		res.send(200);
	});
});

module.exports = router;
