var express = require('express');
var menuItemModel = require('../../models/menuItemModel');

var router = express.Router();

router.get('/', function(req, res){
	menuItemModel.getAll(function (err, orders) {
		if (err) {
			res.send(500);
		}
		res.json(orders);
	});
});

router.get('/:menuItemId', function (req, res) {
	menuItemModel.get(req.params.menuItemId, function (err, ticket) {
		if (err) {
			res.send(500);
		}
		res.json(ticket);
	});
});

router.post('/', function (req, res) {
	//req.body.picture_path = req.files.picture.path;
	req.body.picture_path = 'sample.jpg';
	menuItemModel.add(req.body, function (err, id) {
		if (err) {
			res.send(500);
		}

		res.send(id.toString());
	});
});

router.put('/:menuItemId/photo', function (req, res) {
	menuItemModel.updatePhoto(req.params.menuItemId, req.files.picture.name, function (err, path) {
		if (err) {
			res.send(500);
		}
		res.send(path);
	})
});

router.put('/', function (req, res) {
	menuItemModel.update(req.body, function (err) {
		if (err) {
			res.send(500);
		} else {
			res.send(200);
		}
	});
});

router.delete('/:id', function (req, res) {
	menuItemModel.remove(req.params.id, function (err) {
		if (err) {
			res.send(500);
		} else {
			res.send(200);
		}
	});
});

module.exports = router;
