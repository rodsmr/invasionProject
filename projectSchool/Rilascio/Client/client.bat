@echo off

set DIR=C:\Users\685529\Desktop\Rilascio\

set CLASSPATH=.;%DIR%Client\Client.jar;%DIR%Common\Common.jar

java -cp "%CLASSPATH%" -Djava.security.policy="%DIR%\Common\java.policy" avviaClient.StartGame
