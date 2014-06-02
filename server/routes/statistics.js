var express = require('express');
var router = express.Router();
routeHelper = require('../routes/routeHelper');
var menuItemModel = require('../models/menuItemModel');

router.get('/', function (req, res) {
	routeHelper.redirectIfLoggedOut(req, res, function(loggedIn) {
		if(loggedIn){
			var sortBy = 'numberOrdered', cutoff = null, ascending = false;
			if(req.query.sortBy && req.query.sortBy !== '') {
				sortBy = req.query.sortBy;
			}
			if(req.query.cutoff && req.query.cutoff !== '') {
				//TODO something with cutoff
			}
			
			if(req.query.ascending === 'true'){
				ascending = true;
			}
			//first arg is cutoff date,  not currently implemented
			//second arg is sortBy, starts at "numberOrdered"
			//third arg is whether it is ascending or descending, starts descending
			menuItemModel.getAllWithStatistics(cutoff, sortBy, ascending, function (err, menu_items) {
				if (err) {
					res.send(500, 'error connecting to database');
				}
				res.render('statistics', {user: {email: req.cookies.email}, items: menu_items});
			});
		}
	});
});

module.exports = router;
