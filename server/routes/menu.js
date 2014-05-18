var express = require('express');
var router = express.Router();

router.get('/', function(req, res){
  res.render('menu', {user : {name: 'Phillip'}});
});

module.exports = router;
