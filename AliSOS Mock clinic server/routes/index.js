var express = require('express');
var router = express.Router();
var cors = require('cors');
var path = require('path');


var pgp = require("pg-promise")();
var dbUrl;
try {
    dbUrl = require('./../dbConfig');
} catch (e) {
    console.log('no config file');
}
var db = pgp(process.env.DB_URL || dbUrl.postgresUrl);


/* GET home page. */
router.get('/index', function (req, res, next) {
    res.render('index', {title: 'AliSOS Mock clinic'});
});


router.get('/clinic-ui', function (req, res, next) {
    res.sendFile(path.join(__dirname, '../public/clinic-ui/index.html'));
});

/* patients list */
router.get('/patients', function (req, res, next) {
    db.query("SELECT * FROM public.patient")
        .then(function (data) {
            res.render('patients', {queryData: data});
        })
        .catch(function (error) {
            console.log("ERROR:", error);
        });
});

/* patients API */
router.get('/api/patients', cors(), function (req, res, next) {
    db.query("SELECT * FROM public.patient")
        .then(function (data) {
            res.send(data);
        })
        .catch(function (error) {
            console.log("ERROR:", error);
        });
});


router.get('*', function (req, res, next) {
    res.redirect('/clinic-ui');
    res.render('index', {title: 'AliSOS Mock clinic'});
});


router.post('/api/patient', function (req, res, next) {

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

        db.query(
            "INSERT INTO public.patient (\"userid\", \"anamnesis\", \"address\", \"phone\", \"doctortype\", \"time\", \"id\") VALUES ($1, $2, $3, $4, $5, to_timestamp($6 / 1000.0), DEFAULT)",
            [patient.userId, patient.anamnesis, patient.address, patient.phone, patient.doctorType, patient.timestamp])
            .then(function () {
                res.send(JSON.stringify(patient));
            })
            .catch(function (error) {
                console.log("ERROR:", error);
            });

    } else {
        res.status(400).send('incorrect patient data');
    }


});

module.exports = router;
