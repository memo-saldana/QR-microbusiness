var mongoose = require("mongoose"),
		passportLocalMongoose = require("passport-local-mongoose");

var productSchema = new mongoose.Schema({
	pName: String,
	price: Number,
	productType: String,
	owner: {
		type: mongoose.Schema.Types.ObjectId,
		ref: "User"
	},

});

productSchema.plugin(passportLocalMongoose);

module.exports = mongoose.model("Product", productSchema)