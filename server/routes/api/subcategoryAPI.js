var express = require('express');
var subcategoryModel = require('../../models/subcategoryModel');
var menuItemModel = require('../../models/menuItemModel');

var router = express.Router();

router.get('/', function(req, res){
	subcategoryModel.getAll(function (err, orders) {
		if (err) {
			throw err;
		}
		res.json(orders);
	});
});

router.get('/:subcategoryId', function (req, res) {
	subcategoryModel.get(req.params.subcategoryId, function (err, ticket) {
		if (err) {
			throw err;
		}
		res.json(ticket);
	});
});

router.post('/', function (req, res) {
	subcategoryModel.add(req.body, function (err, id) {
		if (err) {
			throw err;
		}

		res.send(id.toString());
	});
});

router.put('/', function (req, res) {
	subcategoryModel.update(req.body, function (err) {
		if (err) {
			throw err;
		} else {
			res.send(200);
		}
	});
});

router.delete('/:id', function (req, res) {
	menuItemModel.removeAllInSubcategory(req.params.id, function(err) {
		if(err) {
			throw err;
			}
		else {
			subcategoryModel.remove(req.params.id, function (err) {
				if (err) {
					throw err;
				} else {
					res.send(200);
				}
			});
		}
	});
});

module.exports = router;
