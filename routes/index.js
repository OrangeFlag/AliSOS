var express = require('express');
var router = express.Router();


var pgp = require("pg-promise")(/*options*/);
var db = pgp("postgres://lbxsqmas:Bohr3t9BKkJ3AsjLaG2qUM1S00ixg4pA@balarama.db.elephantsql.com:5432/lbxsqmas");

/* GET home page. */
router.get('/', function (req, res, next) {
    res.render('index', {title: 'AliSOS Mock clinic'});
});

router.post('/patient', function (req, res, next) {
    const patient = {
        "userId": req.body.userId,
        "anamnesis": req.body.anamnesis,
        "address": req.body.address,
        "phone": req.body.phone
        //"doctorType": req.body.doctorType
    }
    if (req.body.doctorType) {
        patient.doctorType = req.body.doctorType;
    }
    if (patient.userId && patient.phone && patient.anamnesis && patient.address) {
        if (patient.doctorType) {

            db.query(
                "INSERT INTO public.patient (\"userid\", \"anamnesis\", \"address\", \"phone\", \"doctortype\", \"id\") VALUES ($1, $2, $3, $4, $5, DEFAULT)",
                [patient.userId, patient.anamnesis, patient.address, patient.phone, patient.doctorType])
                .then(function () {
                    res.send(JSON.stringify(patient));
                })
                .catch(function (error) {
                    console.log("ERROR:", error);
                });
        } else{
            db.query(
                "INSERT INTO public.patient (\"userid\", \"anamnesis\", \"address\", \"phone\", \"id\") VALUES ($1, $2, $3, $4, DEFAULT)",
                [patient.userId, patient.anamnesis, patient.address, patient.phone])
                .then(function () {
                    res.send(JSON.stringify(patient));
                })
                .catch(function (error) {
                    console.log("ERROR:", error);
                });
        }
    } else {
        res.status(412).send('incorrect patient data');
    }


});

module.exports = router;
