var express = require('express');
var menuItemModel = require('../../models/menuItemModel');

var router = express.Router();

router.get('/', function(req, res){
	menuItemModel.getAll(function (err, orders) {
		if (err) {
			throw err;
		}
		res.json(orders);
	});
});

router.get('/:menuItemId', function (req, res) {
	menuItemModel.get(req.params.menuItemId, function (err, ticket) {
		if (err) {
			throw err;
		}
		res.json(ticket);
	});
});

router.post('/', function (req, res) {
	//req.body.picture_path = req.files.picture.path;
	req.body.picture_path = 'sample.jpg';
	menuItemModel.add(req.body, function (err, id) {
		if (err) {
			throw err;
		}

		res.send(id.toString());
	});
});

router.post('/:menuItemId/photo', function (req, res) {
	console.log(JSON.stringify(req.files));
	menuItemModel.updatePhoto(req.params.menuItemId, req.files.file.name, function (err, path) {
		if (err) {
			throw err;
		}
		res.send(path);
	})
});

router.put('/', function (req, res) {
	menuItemModel.update(req.body, function (err) {
		if (err) {
			throw err;
		} else {
			res.send(200);
		}
	});
});

router.delete('/:id', function (req, res) {
	menuItemModel.remove(req.params.id, function (err) {
		if (err) {
			throw err;
		} else {
			res.send(200);
		}
	});
});

module.exports = router;
