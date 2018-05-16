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
  <p>========</p>
  <script type="text/javascript" charset="UTF-8">
    var webSocket =
      new WebSocket('ws://192.168.1.111:9000/elimination/websocketNext');
   
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
  </body>
  </html>
