var express = require('express');
var menuItemModel = require('../../models/menuItemModel');
var routeHelper = require('../routeHelper');

var router = express.Router();

router.get('/', function(req, res){
	menuItemModel.search({deleted: 0}, function (err, menuItems) {
		if (err) {
			return routeHelper.jsonError(res, err);
		}
		res.json(menuItems);
	});
});

router.get('/:menuItemId', function (req, res) {
	menuItemModel.get(req.params.menuItemId, function (err, ticket) {
		if (err) {
			return routeHelper.jsonError(res, err);
		}
		res.json(ticket);
	});
});

router.post('/', function (req, res) {
	//req.body.picture_path = req.files.picture.path;
	req.body.picture_path = 'sample.jpg';
	menuItemModel.add(req.body, function (err, id) {
		if (err) {
			return routeHelper.jsonError(res, err);
		}

		res.send(id.toString());
	});
});

router.post('/:menuItemId/photo', function (req, res) {
	console.log(JSON.stringify(req.files));
	menuItemModel.updatePhoto(req.params.menuItemId, req.files.file.name, function (err, path) {
		if (err) {
			return routeHelper.jsonError(res, err);
		}

		res.send(path);
	})
});

router.put('/', function (req, res) {
	menuItemModel.update(req.body, function (err) {
		if (err) {
			return routeHelper.jsonError(res, err);
		}

		res.send(200);
	});
});

router.delete('/:id', function (req, res) {
	var menuItem = {menu_item_id: req.params.id, deleted: 1};
	menuItemModel.update(menuItem, function (err) {
		if (err) {
			return routeHelper.jsonError(res, err);
		}

		res.send(200);
	});
});

module.exports = router;
