<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Translation Program</title>
<style type="text/css">
p {text-indent: 2em; margin: 5px; font-size: 4vw;}
 span {color:DeepSkyBlue}
 .icon{width:68px;height:68px; background:url('./background/a.jpg');display:inline-block;}
 .ss {
color:	Crimson
}
.word{color:	DarkOliveGreen}
#player{  
    position:fixed;   
    top:100px;   
  
    
} 
</style>
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script>
		function hide(ob) {
			var id = $(ob).attr("id");

			var iNum1 = parseInt(id);
			iNum1 += 1;

			$("#" + iNum1).slideToggle(100);

		}
	</script>
</head>
<body>
  <audio src=".\soundtrack\time.mp3" id="player" controls="controls"></audio>
	<input type="button" value="播放"  id="0.mp3" onclick="playAll(this.id)" />
  <p id="messages"/>
  <script type="text/javascript" charset="UTF-8">
    var webSocket =
      new WebSocket('ws://192.168.1.110:9000/elimination/websocketMain');
   
    webSocket.onerror = function(event) {
        onError(event)
      };
   
      webSocket.onopen = function(event) {
        onOpen(event)
      };
   
      webSocket.onmessage = function(event) {
        onMessage(event)
      };
   
      function onMessage(event) {
        document.getElementById('messages').innerHTML
          += '' + event.data;
      }
   
      function onOpen(event) {
        document.getElementById('messages').innerHTML
          = '';
      }
   
      function onError(event) {
       
      }
      function start() {
    	  var login_name='1';
    	  login_name = encodeURI(login_name); 
          webSocket.send(login_name);
          return false;
        }
      function reSave(value) {
    	  var loginname = encodeURI(value); 
          webSocket.send(loginname);
          return false;
        }
      
      setTimeout(start,1000);
    </script>

 <script>
var sh;
sh=self.setInterval(ts,2000);
var js;
function ts() {
var psize=document.getElementById('csb').value;
	         js=psize;
    var aDiv = document.getElementsByTagName('div');
    var arr = [];
    for(var i=0;i<aDiv.length;i++)
    {
        arr.push(aDiv[i]);  //aDiv是元素的集合，并不是数组，所以不能直接用数组的sort进行排序。
    }
    arr.sort(function(a,b){return a.getAttribute('data-id') - b.getAttribute('data-id')});
   for(var i=0;i<arr.length;i++)
    {
        document.body.appendChild(arr[i]); //将排好序的元素，重新塞到body里面显示。
    }
   
   
   if(aDiv.length == psize ) {
	 clearInterval(sh);
	 document.getElementById('messages').innerHTML
     = '翻译结束...';
	 var box=document.getElementsByClassName("hometown");
	 box.style.display="none"; 
	 
          
   }
  }
</script>

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
			player.setAttribute('src', './soundtrack/news/${requestScope.contentid}/'+value);

			player.play(); /*播放*/

			player.onended = function() {
				player.src = './soundtrack/news/${requestScope.contentid}/'+ss.shift()+'.mp3';
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
			player.setAttribute('src', './soundtrack/news/${requestScope.contentid}/'+value);
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
