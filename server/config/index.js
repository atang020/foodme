module.exports= function(mode) {
	console.log(mode, process.env.NODE_ENV);
	return require('./' + (mode || process.env.NODE_ENV || 'development') + '.json');
};
