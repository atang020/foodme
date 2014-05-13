var express = require('express');
var router = express.Router();

router.get('/', function(req, res){
  res.send('This is the order page.');
});

router.get('/active', function(req, res){
  res.send('Welcome to the active orders page');
});

router.get('/archived', function(req, res){
  res.render('index', {title: 'Cpt', fubar: 'Private'});
});

module.exports = router;
