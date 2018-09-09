var express = require("express"),
  router = express.Router(),
  passport = require("passport"),
  User = require("../models/user"),
  Product = require("../models/product"),
  Sales = require("../models/sales");

// Show all products of a given business
router.get("/index", function(req, res) {
  User.findById(req.user._id, function(err, currUser) {
    if (err) {
      console.log(err);
    } else {
      res.render("/products/index", { products: currUser.products });
    }
  });
});

router.get("/new", isLoggedIn, function(req, res) {
  res.render("products/new");
});

router.post("/", isLoggedIn, function(req, res) {
  User.findById(req.user._id, function(err, currUser) {
    if (err) {
      console.log(err);
    } else {
      Product.create(
        {
          pName: req.body.pName,
          productType: req.body.productType,
          price: req.body.precio
        },
        function(err, product) {
          if (err) {
            console.log(err);
          } else {
            currUser.products.push(product);
            currUser.save();
            console.log(product);
            res.redirect("/landing");
          }
        }
      );
    }
  });
});

function isLoggedIn(req, res, next) {
  if (req.isAuthenticated()) {
    return next();
  }
  res.redirect("/login");
}

module.exports = router;
