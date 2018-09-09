var express = require("express"),
		app = express(),
		bodyParser = require("body-parser"),
		mongoose = require("mongoose"),
		passport = require("passport"),
		LocalStrategy = require("passport-local"),
		methodOverride = require("method-override"),
		User = require("./models/user"),
		Product = require("./models/product");

		// seedDB = require("./seeds");

// requiring routes
var indexRoutes = require("./routes/index"),
		qrRoutes = require("./routes/qr")

mongoose.connect("mongodb://localhost:27017/yelp_camp", { useNewUrlParser: true });
app.use(bodyParser.urlencoded({extended: true}));	
app.set("view engine","ejs");
app.use(express.static(__dirname + "/public"));
app.use(methodOverride("_method"));

// seedDB(); seed the database

// Passport Config
app.use(require("express-session")({
	secret: "This time Jaina will win because she is a very cute cat.",
	resave: false,
	saveUninitialized: false
}))

app.use(passport.initialize());
app.use(passport.session());
passport.use(new LocalStrategy(User.authenticate()));
passport.serializeUser(User.serializeUser());
passport.deserializeUser(User.deserializeUser());

app.use(function(req,res, next) {
	res.locals.currentUser = req.user;
	next();
})

// Campground.create(
// 	{
// 		name: "Mountain Goat's Rest", 
// 		image: "https://upload.wikimedia.org/wikipedia/commons/a/a7/Gypsy_camp%2C_Bekonscot.JPG",
// 		description:"This is a mountain for a goat to rest in."
	
// 	}, function(err,campground) {
// 		if(err){
// 			console.log(err);
// 		} else {
// 			console.log("Created:");
// 			console.log(campground);
// 		}
// 	});

app.use("/",indexRoutes);
app.use("/qr", qrRoutes);

app.listen(3000,function() {
	console.log("YelpCamp started on port 3000");
});