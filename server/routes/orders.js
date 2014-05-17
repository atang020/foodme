var express = require('express');
var router = express.Router();

router.get('/', function(req, res){
  res.render('orders', {orders: [
  {id: 1, table: 5, item: 'Ritz Crackers and Salami', status: 'ERROR: guest starved to death'},
  {id: 2, table: 2, item: 'Fried Cheerios', status: 'definitely not on fire'},
  {id: 3, table: 7, item: 'Coffee', notes: 'no cyannide', status: 'pretty good'},
  {id: 4, table: 15, item: 'Small Fries', status: 'undergoing mitosis'}
  ], user : {name: 'Phillip'}});
});

router.get('/active', function(req, res){
  res.send('Welcome to the active orders page');
});

router.get('/archived', function(req, res){
  res.render('index', {title: 'Cpt', fubar: 'Private'});
});

module.exports = router;
