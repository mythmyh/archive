<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<style type="text/css">
p {text-indent: 2em; margin: 5px; font-size: 4vw;}
 span {color:DeepSkyBlue}
 .icon{width:68px;height:68px; background:url('a.jpg');display:inline-block;}
 .ss {
color:	Crimson
}
.word{color:	DarkOliveGreen}
#player{  
    position:fixed;   
    top:100px;     
} 
</style>
<script type="text/javascript" src="jquery-3.3.1.js"></script>
<script>
		function hide(ob) {
			var id = $(ob).attr("id");
			var iNum1 = parseInt(id);
			$("#" + iNum1).slideToggle(100);

		}
	</script>
</head>
<body>
<audio src=".\soundtrack\time.mp3" id="player" controls="controls"></audio>
<input type="button" value="播放"  id="0.mp3" onclick="playAll(this.id)" />
<script>
          
		var tt = [];
		
		var ss = [];
	
		function playAll(value) {
			 
			if ( tt === undefined || tt.length == 0) {
			    // array empty or does not exist
				for (var i=1;i< js;i++){
					tt.push(i);
				}
				ss = ss.concat(tt);
			}
		
			// document.getElementsByTagName('img')[0].setAttribute('src','3.jpg');
			var player = $("#player")[0]; /*jquery对象转换成js对象*/
			player.setAttribute('src', './soundtrack/news/'+value);

			player.play(); /*播放*/

			player.onended = function() {
				player.src = './soundtrack/news/'+ss.shift()+'.mp3';
				player.play();
				if (ss.length == 0) {

					player.pause();
					ss = ss.concat(tt);

				}

			}

		}
	</script>
	
	
	<script>
	
		function playSolo(value) {
		
			// document.getElementsByTagName('img')[0].setAttribute('src','3.jpg');
			var player = $("#player")[0]; /*jquery对象转换成js对象*/
			player.setAttribute('src', './soundtrack/news/'+value);
			if (player.paused){ /*如果已经暂停*/
	            player.play(); /*播放*/
	        }else {
	            player.pause();/*暂停*/
	        }

		}
	</script>
	
	<span  class ="back"></span>
  </body>
  </html>