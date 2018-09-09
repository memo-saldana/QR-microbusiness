var mongoose = require("mongoose"),
		passportLocalMongoose = require("passport-local-mongoose");

var salesSchema = new mongoose.Schema({
	productSold: {
		id: {
			type: mongoose.Schema.Types.ObjectId,
			ref: "Product"
		},
		pName: String,
		productType: String,
		price: Number,
		
	},


});