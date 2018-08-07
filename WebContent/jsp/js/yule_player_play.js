var flashPlayer,moveTimer,_$;

_$=function(id){
	return document.getElementById(id);	
}
var getFlashMovieObject=function(movieName)  
{
	flashPlayer=!flashPlayer?flashPlayer=document[movieName]:flashPlayer;
  	return flashPlayer;
} 

var StopFlashMovie=function()
{
	clearMoveTimer();
	var flashMovie=getFlashMovieObject("Myflash");
	flashMovie.StopPlay();
	PlayAndStop();
}

var PlayFlashMovie=function()
{
	clearMoveTimer();
	var flashMovie=getFlashMovieObject("Myflash");
	if(flashMovie.IsPlaying()){
		StopFlashMovie();
	}else{
	   flashMovie.Play();
	}
	   PlayAndStop();
}
var PlayAndStop=function(){	
	var p=_$('p_play'),s=_$('p_stop'),
		flashMovie=getFlashMovieObject("Myflash");
	if(flashMovie.IsPlaying()){
		p.className=p.className.replace("disn","");
		//s.style.display='none';
		//p.style.display='block';
	}else{
		//s.style.display='block';
		//p.style.display='none';
	}							
}

var StopFlashMovie1 = function () {
    clearMoveTimer();
    var flashMovie = getFlashMovieObject("Myflash");
    flashMovie.StopPlay();
    PlayAndStop1();
}

var PlayFlashMovie1 = function () {
    clearMoveTimer();
    var flashMovie = getFlashMovieObject("Myflash");
    flashMovie.Play();
    PlayAndStop1();
}
var PlayAndStop1 = function () {
    var p = _$('pp_play'), s = _$('pp_stop'),
		flashMovie = getFlashMovieObject("Myflash");
    if (flashMovie.IsPlaying()) {
        s.className = s.className.replace("disn", "");
        p.style.display = 'none';
        s.style.display = 'block';
    } else {
        p.style.display = 'block';
        s.style.display = 'none';
    }
}

var RewindFlashMovie = function () {
    clearMoveTimer();
    var flashMovie = getFlashMovieObject("Myflash");
    _$('p_play_span').className = "pd_play_start";
    _$('p_play_span2').className = "pd_play_start2";
    flashMovie.Rewind();
    
}

var ForwordFlashMovie = function () {
    if (!moveTimer) moveTimer = setInterval(function () { moveFlashFrame(10) }, 10);
    _$('p_play_span').className = "pd_play_start";
    _$('p_play_span2').className = "pd_play_start2";
}
var BackrewindFlashMovie=function(){
    if (!moveTimer) moveTimer = setInterval(function () { moveFlashFrame(-10) }, 100);
    _$('p_play_span').className = "pd_play_start";
    _$('p_play_span2').className = "pd_play_start2";
}
var clearMoveTimer=function(){
	moveTimer&&clearInterval(moveTimer);
	moveTimer=null;
}

var moveFlashFrame=function(range){
	var flashMovie,currentFrame,totalFrame,nextFrame;
	flashMovie=getFlashMovieObject("Myflash");	
	currentFrame=flashMovie.TGetProperty("/",4);
	totalFrame=flashMovie.TGetProperty("/",5);
	nextFrame=parseInt(currentFrame)+range;
	if (nextFrame>=totalFrame||nextFrame<=0){nextFrame=0;RewindFlashMovie();}
	flashMovie.GotoFrame(nextFrame);
}

var maxWindow=function(){
    var getHeight = document.documentElement.clientHeight ;
    var getWidth = document.documentElement.clientWidth;
    var isChrome = window.navigator.userAgent.indexOf("Chrome") !== -1;
    document.body.style.overflow = 'hidden';     
	_$("flashContainer").style.position = "fixed"; 
    if (getWidth / getHeight > 600 / 414) {
        _$("flashContainer").style.height =document.documentElement.clientHeight-40 + "px";
		
        _$("flashContainer").style.width = document.documentElement.clientWidth + "px";
     
        _$("flashContainer").style.zIndex = "9999";
        _$("flashContainer").style.left = "0px";
     
        _$("flashContainer").style.top = "0px";
      
    } else {
        _$("flashContainer").style.width = document.documentElement.clientWidth + "px";
        _$("flashContainer").style.height = document.documentElement.clientHeight - 40 + "px";
      
        _$("flashContainer").style.zIndex = "9999";
        _$("flashContainer").style.top = "0px";
        _$("flashContainer").style.left =  "0px";
        _$("bd_ads").style.display = "block";
        _$("ad1").style.display = "block";
    }

	_$("bofang").style.display = "none";
_$("closeArea").style.display = "block";
_$("pp_play").style.display = "block";
//_$("pp_stop").style.display = "block"; 

_$("closeflash2").style.display = "block";

}

var normalWindow = function () {
	_$("bofang").style.display = "block";
    _$("flashContainer").style.position = "static";
    _$("closeArea").style.display = "none";
    _$("pp_play").style.display = "none";
    //    _$("pp_stop").style.display = "none"; 
    
    _$("closeflash2").style.display = "none";

    //    _$("ad1").style.display = 'block';
    _$("flashContainer").style.height = "566px";
    _$("flashContainer").style.width = "1000px"; 
    document.body.style.overflow = 'visible';
}
 
// JavaScript Document
document.onkeydown = function (e) {
    var ee = window.event ? window.event : e;
    if (ee.keyCode == 27) {
        normalWindow();
    }
}

function btnNextClick() {
    try {
        window.open(_$("pa-next").href, "_self");
    } catch (e) { }
}

function btnPreviousClick() {
    try {
        window.open(_$("pa-previous").href, "_self");
    } catch (e) { }
}
 
