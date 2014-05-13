var express = require('express');
var router = express.Router();

router.get('/', function(req, res){
  res.send('message has been sent');

});

module.exports = router;
