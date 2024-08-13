var express = require('express'),
    http = require("http"),
    mongoose = require('mongoose'),
    app = express();


app.use(express.static(__dirname + "/cliente"));

app.use(express.urlencoded);

mongoose.connect('mongodb://localhost/biglietteriaAutobus');

var prenotazioneSchema = mongoose.Schema({

    partenza: String,
    arrivo: String,
    data: Date,
    numBiglietti: Number,
    numBagagli: Number

});

var prenotazione = mongoose.model("Prenotazione", prenotazioneSchema);

http.createServer(app).listen(3000);

// POST /prenotazione crea una nuova prenotazione

app.post("/prenotazione", function(req, res){

    console.log(req.body);

    var newPrenotazione = new prenotazione({
        "partenza": req.body.partenza, 
        "arrivo": req.body.arrivo,
        "data": req.body.data,
        "numBiglietti": req.body.numBiglietti,
        "numBagagli": req.body.numBagagli
    });

    newPrenotazione.save(function(err, result){
        if(err !== null){

            console.log(err);
            res.send("ERROR");

        }else{

            prenotazione.find({}, function(err, result){

                if(err !== null){

                    console.log(err);
                    res.send("ERROR");

                }

                res.json(result);

            })

        }
    })

})

