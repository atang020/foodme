module.exports= function(mode) {
	return require('./' + (mode || (process.argv[2] === 'production' ? 'production' : undefined) || process.env.NODE_ENV || 'development') + '.json');
};
