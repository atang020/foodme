var express = require('express');
var router = express.Router();

router.get('/', function(req, res){
	var myMenu = {'drinks':{}, 'appetizers':{}, 'entrees':{}, 'desserts':{}};
	
	//NOTE! subcategory names can't have spaces (at least for now)
	myMenu.drinks.subcategories = [{name: 'soda', items: []}, {name: 'beer', items: []}, {name: 'double-beer', items: []}];
	myMenu.appetizers.subcategories = [{name: 'soups', items: []}, {name: 'salads', items: []}];
	myMenu.entrees.subcategories = [{name: 'pasta', items: []}, {name: 'chicken', items: []}, {name: 'curries', items: []}, ];
	myMenu.desserts.subcategories = [{name: 'unhealthy-desserts', items: []}, {name: 'even-more-unhealthy-desserts', items: []}];
	
	myMenu.drinks.subcategories[0].items = [{name: 'small soda', price: '$1.49'}, 
											{name: 'medium soda', price: '$2.19'},
											{name: 'large soda', price: '$2.49'}];
	
	res.render('menu', {user : {name: 'Phillip'}, categories: myMenu});
});

module.exports = router;
