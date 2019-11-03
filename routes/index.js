var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'AliSOS Mock clinic' });
});

router.post('/patient', function (req, res, next) {
  const patient = {
      "userId": req.body.userId,
      "anamnesis": req.body.anamnesis,
      "address": req.body.address,
      "phone": req.body.phone
      //"doctorType": req.body.doctorType
  }
  if(req.body.doctorType){
      patient.doctorType = req.body.doctorType;
  }
  if(patient.userId && patient.phone && patient.anamnesis && patient.address){
      res.send(JSON.stringify(patient));
  } else{
      res.status(412).send('incorrect patient data');
  }


});

module.exports = router;
