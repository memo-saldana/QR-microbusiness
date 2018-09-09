var mongoose = require("mongoose"),
		passportLocalMongoose = require("passport-local-mongoose");

var userSchema = new mongoose.Schema({
	bName: String,
	username: String,
	password: String,
	clabe: String,
	AccType: String,
	products: [{
		type: mongoose.Schema.ObjectId,
		ref: "Product"
	}]
});

userSchema.plugin(passportLocalMongoose);

module.exports = mongoose.model("User",userSchema)