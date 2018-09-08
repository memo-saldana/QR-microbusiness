var express = require("express"),	
	app = express(),
	bodyParser = require("body-parser"),
	mongoose = require("mongoose"),
	passport = require("passport"),
	User = require("./models/user");
	router = express.Router();

mongoose.connect("mongodb://localhost:27017/qr-microbusiness", { useNewUrlParser: true });

app.use(bodyParser.urlencoded({extended: true}));
app.use(logger('dev'));

require('./routes')(router);
app.use('/api/v1', router);

app.listen(process.env.PORT || 3000, function() {
	console.log("App running, you can start using android app.");
});