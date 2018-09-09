var express = require("express"),
		router = express.Router(),
		passport = require("passport"),
		User = require("../models/user"),
		Product = require("../models/product"),
		Sales = require("../models/sales");

// Show all products of a given business
router.get("/",function(req,res) {
})