var mainPicture = 0;
var maxPictures = 4;
var number = mainPicture;
var timer1;
var timer2;

function slider(){

    $("#slider").fadeIn(500);
    if(number === mainPicture || number > maxPictures){
        number = mainPicture;
        timer1 = setTimeout(slider, 15000);
        timer2 = setTimeout(hide, 14500);
    }
    else{
        timer1 = setTimeout(slider, 2000);
        timer2 = setTimeout(hide, 1500);
    }

    var plik = "<img src=\/img/image/" + number + ".jpg\  alt='slider-picture'/>";
    document.getElementById("slider").innerHTML=plik;
    number++;
}

function hide(){
    $("#slider").fadeOut(500);
}