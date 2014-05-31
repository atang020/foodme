var express = require('express');
var path = require('path');
var favicon = require('static-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var multer = require('multer');

var routes = require('./routes/index');
var users = require('./routes/users');
var orders = require('./routes/orders');
var menu = require('./routes/menu');
var statistics = require('./routes/statistics');

var ticketAPI = require('./routes/api/ticketAPI');
var menuItemAPI = require('./routes/api/menuItemAPI');
var reviewAPI = require('./routes/api/reviewAPI');
var subcategoryAPI = require('./routes/api/subcategoryAPI');
var ticketItemAPI = require('./routes/api/ticketItemAPI');
var userAPI = require('./routes/api/userAPI');

var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use(favicon());
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded());
app.use(multer({
	dest: '/root/foodme/server/public/uploads/'}));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));


//Website routes
app.use('/',routes);
app.use('/users', users);
app.use('/orders', orders);
app.use('/menu', menu);
app.use('/statistics', statistics);

//APIs
app.use('/api/tickets', ticketAPI);
app.use('/api/menuitems', menuItemAPI);
app.use('/api/users', userAPI);
app.use('/api/reviews', reviewAPI);
app.use('/api/subcategories', subcategoryAPI);
app.use('/api/ticketitems', ticketItemAPI);

/// catch 404 and forwarding to error handler
app.use(function (req, res, next) {
	var err = new Error('Not Found');
	err.status = 404;
	next(err);
});

/// error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
	app.use(function (err, req, res, next) {
		res.status(err.status || 500);
		res.render('error', {
			message: err.message,
			error: err
		});
		//next();
	});
}

// production error handler
// no stacktraces leaked to user
app.use(function (err, req, res, next) {
	res.status(err.status || 500);
	res.render('error', {
		message: err.message,
		error: err
	});
	//next();
});


module.exports = app;
