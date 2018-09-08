var mongoose = require("mongoose"),
		passportLocalMongoose = require("passport-local-mongoose");

var userSchema = new mongoose.Schema({
	
	name: 				String,
	email: 				String,
	password: 		String,
	created_at: 	String

});

mongoose.Promise = global.Promise;

module.exports = mongoose.model("User",userSchema)