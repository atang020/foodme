var assert = require('assert');
var menuItem = require('../models/menu_item').menuItem;
var verify = require('../models/menu_item').verify;

describe('Menu Items', function() {

	beforeEach(function () {
      this.menuItem = new menuItem('taco', 'fubar', '2');
   });
   
   it('name should have been added', function() {
	  assert.equal(this.menuItem.name, 'taco');
   });
   
   it('description should have been added', function(){
		assert.equal(this.menuItem.description, 'fubar');
   });
   
   it('price is wrong', function(){
		assert.equal(this.menuItem.price, '2');
   });	
   
   it('verify fails', function(){
		assert.equal(verify(this.menuItem), null);
   });
});