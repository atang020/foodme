module.exports= function(mode) {
	return require('./' + (mode || process.argv[2] || process.env.NODE_ENV || 'development') + '.json');
};
