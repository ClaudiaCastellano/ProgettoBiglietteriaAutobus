var main = function(){
    "use strict";


    $(".form button").on("click", function(event){

        var partenza = document.getElementById('partenza').value;
        console.log("Città di partenza: " + partenza);

        var arrivo = document.getElementById('arrivo').value;
        console.log("Città di arrivo: " + arrivo);

        var data = document.getElementById('data-partenza').inputElement.value;
        console.log("Data: " + data);

        var numBiglietti = document.getElementById('numeroBiglietti').value;
        console.log("Numero Biglietti: " + numBiglietti);

        var numBagagli = document.getElementById('numeroBagagli').value;
        console.log("Numero Bagagli: " + numBagagli);

        var prenotazione = {
            partenza,
            arrivo,
            data,
            numBiglietti,
            numBagagli
        };

        $.ajax({

            url: "./prenotazione",
            type: 'PUT',
            data: prenotazione,
            success: function(result){

                console.log(result);

            }

        });


    });





}


/*var salvaInput = function(){

    var partenza = document.getElementById('partenza').value;
    console.log("Città di partenza: " + partenza);

    var arrivo = document.getElementById('arrivo').value;
    console.log("Città di arrivo: " + arrivo);

    var data = document.getElementById('data-partenza').inputElement.value;
    console.log("Data: " + data);

    var numBiglietti = document.getElementById('numeroBiglietti').value;
    console.log("Numero Biglietti: " + numBiglietti);

    var numBagagli = document.getElementById('numeroBagagli').value;
    console.log("Numero Bagagli: " + numBagagli);

    if(numBagagli > numBiglietti){
        console.log("Errore");
    }

}*/

$(document).ready(main);