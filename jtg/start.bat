echo off

#HIER PFAD ZUM JRE BZW. ZUM JDK EINTRAGEN!!!
set JAVA_HOME=C:\jdk1.5.0
#HIER PFAD ZUM JTG-VERZEICHNIS EINTRAGEN!!!
set JTJG_HOME=f:\jtg

cd %JTJG_HOME%
start %JAVA_HOME%\bin\javaw -jar jackTheJGrabber.jar