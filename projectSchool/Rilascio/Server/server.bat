@echo off

set DIR=C:\Users\685529\Desktop\Rilascio\

set CLASSPATH=.;%DIR%Server\Server.jar;%DIR%Common\Common.jar;%DIR%Server\libs\derby.jar;%DIR%Server\libs\derbyLocale_it.jar

java -cp "%CLASSPATH%" -Djava.rmi.server.codebase="file:\%DIR%Common\Common.jar" -Djava.security.policy="%DIR%\Common\java.policy" avviaServer.StartServer
