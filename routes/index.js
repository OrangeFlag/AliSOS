var express = require('express');
var router = express.Router();


var pgp = require("pg-promise")(/*options*/);
var dbUrl = require('./../dbConfig')
var db = pgp(dbUrl.postgresUrl);

/* GET home page. */
router.get('/', function (req, res, next) {
    res.render('index', {title: 'AliSOS Mock clinic'});
});

router.post('/patient', function (req, res, next) {
    const patient = {
        "userId": req.body.userId,
        "anamnesis": req.body.anamnesis,
        "address": req.body.address,
        "phone": req.body.phone,
        "timestamp": Date.now()
    }
    if (req.body.doctorType) {
        patient.doctorType = req.body.doctorType;
    }
    if (patient.userId && patient.phone && patient.anamnesis && patient.address) {
        if (patient.doctorType) {

            db.query(
                "INSERT INTO public.patient (\"userid\", \"anamnesis\", \"address\", \"phone\", \"doctortype\", \"time\", \"id\") VALUES ($1, $2, $3, $4, $5, to_timestamp($6 / 1000.0), DEFAULT)",
                [patient.userId, patient.anamnesis, patient.address, patient.phone, patient.doctorType, patient.timestamp])
                .then(function () {
                    res.send(JSON.stringify(patient));
                })
                .catch(function (error) {
                    console.log("ERROR:", error);
                });
        } else{
            db.query(
                "INSERT INTO public.patient (\"userid\", \"anamnesis\", \"address\", \"phone\", \"time\", \"id\") VALUES ($1, $2, $3, $4, to_timestamp($5 / 1000.0), DEFAULT)",
                [patient.userId, patient.anamnesis, patient.address, patient.phone, patient.timestamp])
                .then(function () {
                    res.send(JSON.stringify(patient));
                })
                .catch(function (error) {
                    console.log("ERROR:", error);
                });
        }
    } else {
        res.status(400).send('incorrect patient data');
    }


});

module.exports = router;
