var express = require("express"),
	app = express(),
	passport = require("passport");




app.post("/login", passport.authenticate("local",
	{
		successRedirect ="/landing",
		failureRedirect ="/login"
	}), function(req,res) {		
});



// LISTENER
app.listen("3000", function() {
	console.log("App running, enter ip address on browser.");
})