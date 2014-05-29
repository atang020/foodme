var express = require('express');
var router = express.Router();

router.get('/', function (req, res) {
	routeHelper.redirectIfLoggedOut(req, res, function(loggedIn) {
		if(loggedIn){
			res.render('statistics', { user: {name: 'Phillip'}});
		}
	});
});

module.exports = router;
