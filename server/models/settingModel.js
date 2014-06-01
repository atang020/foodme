var database = require('./database');

function verify(setting) {
	if (setting.key.length > 45) {
		return new Error('Setting key length must be 45 characters or shorter');
	}
	if (setting.value.length > 45) {
		return new Error('Setting key length must be 45 characters or shorter');
	}

	return null;
}

//Comment to fix a git thing
exports.getAll = function (callback) {
	database.query('SELECT * FROM setting', function (err, rows) {
		if (err) {
			callback(err, null);
			return;
		}

		callback(null, rows);
	});
};

exports.get = function (key, callback) {
	database.query('SELECT * FROM setting WHERE key = ?', [key], function (err, rows) {
		if (err) {
			callback(err, null);
			return;
		}

		if (rows === []) {
			callback(null, null);
			return;
		}

		callback(null, rows[0]);
	});
};

exports.add = function (setting, callback) {
	var err = verify(setting);
	if (err) {
		return callback(err);
	}

	//If not value set to null
	setting.value = setting.value === undefined ? null : setting.value;

	database.query('INSERT INTO setting (key, value)' +
			'VALUES (?, ?)', [setting.key, setting.value],
		function (err, result) {
			if (err) {
				return callback(err);
			}

			callback(null, result.insertId);
		});
};

exports.update = function (setting, callback) {
	var err = verify(setting);
	if (err) {
		return callback(err);
	}

	database.query('UPDATE setting SET value = ? WHERE key = ?', [setting.value, setting.key], callback);
};

exports.remove = function (key, callback) {
	database.query('DELETE FROM setting WHERE key = ?', key, callback);
};
