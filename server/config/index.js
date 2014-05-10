module.exports= function(mode) {
	return require('./' + (mode || process.env.NODE_ENV || 'development') + '.json');
};