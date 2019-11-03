var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.post('/patient', function (req, res, next) {
  const patient = {
      "userId": req.body.userId,
      "anamnesis": req.body.anamnesis,
      "address": req.body.address,
      "phone": req.body.phone,
      "doctorType": req.body.doctorType
  }

  res.send(JSON.stringify(patient));
});

module.exports = router;
