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
				//set the options available to the user
				var menuOptions = {sortBy:[{name: 'most popular', val : 'numberOrdered'},{name: 'most profitable', val : 'profit'}],
								cutoff: [{name: 'today', val : null},{name: 'this week', val : null}, {name: 'this month', val : null}],
								ascending: [{name: 'ascending', val : true},{name: 'descending', val : false}]};
				
				//figure out which one of the options we are currently on
				var currentSortBy = {name: sortBy, val: sortBy, menu_index : -1};
				for(var i = 0; i < menuOptions.sortBy.length; i++) {
					if(currentSortBy.val === menuOptions.sortBy[i].val) {
						currentSortBy.name = menuOptions.sortBy[i].name;
						currentSortBy.menu_index = i;
						break;
					}
				}
				
				var currentCutoff = {name: cutoff, val: cutoff, menu_index : -1};
				for(var i = 0; i < menuOptions.cutoff.length; i++) {
					if(currentCutoff.val === menuOptions.cutoff[i].val) {
						currentCutoff.name = menuOptions.cutoff[i].name;
						currentCutoff.menu_index = i;
						break;
					}
				}
				
				var currentAscending = {name: 'descending', val: false, menu_index : 1};
				if(ascending)
					currentAscending = {name: 'ascending', val: true, menu_index : 0};
				
					
				var currentSettings = {cutoff : currentCutoff, sortBy : currentSortBy, ascending: currentAscending};
				
				res.render('statistics', {user: {email: req.cookies.email}, items: menu_items, currentSettings : currentSettings, menuOptions : menuOptions});
			});
		}
	});
});

module.exports = router;
