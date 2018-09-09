var express = require("express"),
		router = express.Router(),
		passport = require("passport"),
		User = require("../models/user"),
		Product = require("../models/product"),
		Sales = require("../models/sales");

router.get("/new", isLoggedIn, function(req,res) {
	res.render("qrcode/new");	
})

function isLoggedIn(req,res,next) {
	if(req.isAuthenticated()){
		return next();
	}
	res.redirect("/login")
}

module.exports = router;