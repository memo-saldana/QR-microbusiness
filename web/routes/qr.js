var express = require("express"),
		router = express.Router(),
		passport = require("passport"),
		User = require("../models/user"),
		Product = require("../models/product"),
		Sales = require("../models/sales");


// Creation Form
router.get("/new", isLoggedIn, function(req,res) {
	console.log(req.user);
	res.render("qrcode/new");	
})

// redirect to landing
router.get("/",function(req,res) {
	redirect("/landing");
})

// Generate QR code and create sales in db
router.post("/", isLoggedIn, function(req,res) {
	var qrReq = "https://api.qrserver.com/v1/create-qr-code/?data={\"ot\":\"0001\",\"dOp\":[{\"alias\":\"";

	qrReq += req.body.alias +"\"},{\"cl\":";
	
	var refN = req.body.refn;
	var refA = req.body.refa;
	var totalAmount= req.body.amount;
	var cl, type;
	User.findById(req.user._id, function(err,curUser) {
		if(err){
			console.log(err);
		} else {
			qrReq += curUser.clabe + "},{\"type\":" + curUser.AccType +"},{\"refn\":";
		}
	});

	qrReq += req.body.refn + "},{\"refa\":\"" + req.body.refa + "\"},{\"amount\":\"" + req.body.amount + "\"},{\"bank\":\"00012\"},{\"country\":\"MX\"},{\"currency\":\"MXN\"}]}";

	console.log(qrReq);
	res.render("qrcode/show",{url: qrReq});
});

function isLoggedIn(req,res,next) {
	if(req.isAuthenticated()){
		return next();
	}
	res.redirect("/login")
}

module.exports = router;