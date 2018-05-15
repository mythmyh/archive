cd D:\apache\success\webapps\elimination\WEB-INF\classes\com\translation\utils\
cd /d D:
set da="C:\Users\mythm\AppData\Local\Google\Chrome\Application\chromedriver.exe"
java -Dwebdriver.chrome.driver=%da% -jar selenium-server-standalone.jar -role node -hub http://192.168.1.110:4444/grid/register -maxSession 40 -browser "browserName=chrome,maxInstances=40" -port 5555





