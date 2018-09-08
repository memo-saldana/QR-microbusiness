var mongoose = require("mongoose");

var userSchema = new mongoose.Schema({
	
	bName: 				String,
	email: 				String,
	password: 		String,
	created_at: 	String

});

mongoose.Promise = global.Promise;

module.exports = mongoose.model("User",userSchema)