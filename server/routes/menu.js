var express = require('express');
var router = express.Router();

router.get('/', function(req, res){
	var myMenu = {'drinks':{}, 'appetizers':{}, 'entrees':{}, 'desserts':{}};
	
	//NOTE! subcategory names can't have spaces (at least for now)
	myMenu.drinks.subcategories = [{name: 'soda'}, {name: 'beer'}, {name: 'double-beer'}];
	myMenu.appetizers.subcategories = [{name: 'soups'}, {name: 'salads'}];
	myMenu.entrees.subcategories = [{name: 'pasta'}, {name: 'chicken'}, {name: 'curries'}, ];
	myMenu.desserts.subcategories = [{name: 'unhealthy-desserts'}, {name: 'even-more-unhealthy-desserts'}];
	res.render('menu', {user : {name: 'Phillip'}, categories: myMenu});
});

module.exports = router;
