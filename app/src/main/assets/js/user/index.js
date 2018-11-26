setInterval(function(){
var date = new Date;
	document.getElementById('title').innerHTML = date.toUTCString()
},1000)
