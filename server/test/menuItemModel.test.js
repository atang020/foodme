process.env.NODE_ENV = 'test';

var database = require('./database');
var async = require('async');
var menuItemModel = require('../models/menuItemModel');
var subcategoryModel = require('./subcategoryModel');

describe('Menu Item model', function () {
    var menuA = { name: 'menuNameA', description: 'descriptionA', price: 0.00, picture_path: 'pathA'},
		menuB = { name: 'menuNameB', description: 'descriptionB', price: 1.00, picture_path: 'pathB'},
		menuC = { name: 'menuNameC', description: 'descriptionC', price: 2.00, picture_path: 'pathC'};

    //test an empty get
    it('get() returns null when there is no data', function (done) {
        menuItemModel.get(1, function (err, result) {
            assert.ifError(err);
            assert.equal(result, null);
            done();
        });
    });
    //test get all when empty
    it('getAll() returns [] when there is no data', function (done) {
        menuItemModel.getAll(function (err, result) {
            assert.ifError(err);
            assert.equal(result.length, 0);
            done();
        });
    });

    //test getAll with info
    it('add() and getAll()', function (done) {
        async.series([
			function (callback) {
			    menuItemModel.add(menuA, callback);
			},
			function (callback) {
			    menuItemModel.add(menuB, callback);
			},
			function (callback) {
			    menuItemModel.add(menuC, callback);
			}
        ],
		function (err, results) {
		    assert.ifError(err);

		    menuItemModel.getAll(function (err, menu) {
		        assert.ifError(err);

		        //menuA
		        assert.equal(menu[0].menu_item_id, results[0]);
		        assert.equal(menu[0].name, menuA.name);
		        assert.equal(menu[0].description, menuA.description);
		        assert.equal(menu[0].price, menuA.price);
                assert.equal(menu[0].picture_path,menuA.picture_path)

		        //menuB
		        assert.equal(menu[1].menu_item_id, results[1]);
		        assert.equal(menu[1].name, menuB.name);
		        assert.equal(menu[1].description, menuB.description);
		        assert.equal(menu[1].price, menuB.price);
		        assert.equal(menu[1].picture_path, menuB.picture_path)

		        //menuC
		        assert.equal(menu[2].menu_item_id, results[2]);
		        assert.equal(menu[2].name, menuC.name);
		        assert.equal(menu[2].description, menuC.description);
		        assert.equal(menu[2].price, menuC.price);
		        assert.equal(menu[2].picture_path,menuC.picture_path)
		        done();

		    });
		});
    });
    //tests add and get
    it('add() and get()', function (done) {
        menuItemModel.add(menuA, function (err, id) {
            assert.ifError(err);

            menuItemModel.get(id, function (err, result) {
                assert.ifError(err);

                assert.equal(result.menu_Item_Id, id);
                assert.equal(result.name, menuA.name);
                assert.equal(result.description, menuA.description);
                assert.equal(result.price, menuA.price);
                assert.equal(result.picture_path, menuA.picture_path);
                done();
            });
        });
    });


    //check add and update
    it('add() and update()', function (done) {
        menuItemModel.add(menuA, function (err, id) {
            assert.ifError(err);

            menuA.menu_Item_Id = id;
            menuA.name = 'menuNameA';
            menuA.description = 'menuDescriptionA';
            menuA.price = 1.00;
            menuA.picture_path="pathA2"
            menuItemModel.update(menuA, function (err) {
                assert.ifError(err);

                //Get the user now
                menuItemModel.get(menuA.menu_Item_Id, function (err, menuA2) {
                    assert.ifError(err);
                    assert.equal(menuA2.menu_Item_Id, menuA.menu_Item_Id);
                    assert.equal(menuA2.name, menuA.name);
                    assert.equal(menuA2.description, menuA.description);
                    assert.equal(menuA2.price, menuA.price);
                    assert.equal(menuA2.picture_path, menuA.picture_path);
                    done();
                });
            });
        });
    });

    it('add() and then remove()', function (done) {
        menuItemModel.add(menuB, function (err, id) {
            assert.ifError(err);
            menuB.menu_Item_Id = id;
            menuItemModel.remove(menuB, function (err) {
                assert.ifError(err);

                //Now try getting the user
                menuItemModel.get(id, function (err, result) {
                    assert.ifError(err);
                    assert.equal(result, null);
                    done();
                });
            });
        });
    });




});