var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function (req, res) {
	routeHelper.redirectIfLoggedIn(req, res, function (loggedIn) {
		if (!loggedIn) {
			res.render('index', { title: 'FoodMe'});
		}
	});
});

module.exports = router;
