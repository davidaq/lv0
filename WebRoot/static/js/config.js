var CFG = {
	apiUrl : function(path) {
		return path[0].toUpperCase() + path.substr(1) + '.action';
	},
    ann_allowed : ['login', 'register', 'wiki', 'home', 'tourlist', 'wikipage'],
	inits : [],
	dict : {}
};
