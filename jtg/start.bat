@echo off
rem ********************************************

rem HIER PFAD ZUM JRE BZW. ZUM JDK ANPASSEN!
set JAVA_HOME=C:\Programme\Java\jdk1.5.0

rem HIER LAUFWERK ZUM JTJG-VERZEICHNIS ANPASSEN!
set JTJG_DRIVE=c:

rem HIER PFAD ZUM JTJG-VERZEICHNIS ANPASSEN!
set JTJG_HOME=\Programme\JackTheJGrabber

rem ********************************************

:start
%JTJG_DRIVE%
cd %JTJG_HOME%
if not exist %JAVA_HOME%\bin\javaw.exe goto fehler1
if not exist jackTheJGrabber.jar goto fehler2

start %JAVA_HOME%\bin\javaw -jar jackTheJGrabber.jar
goto ende

:fehler1
echo.
echo Die Pfadangaben zu JRE oder JDK in
echo %0
echo muessen korrekt angepasst werden.
echo.
echo Bitte eine Taste druecken.
pause > nul
notepad %0
goto ende

:fehler2
echo.
echo Die Laufwerks- und Pfadangaben zu JtG in
echo %0
echo muessen korrekt angepasst werden.
echo.
echo Bitte eine Taste druecken.
pause > nul
notepad %0
goto ende

:ende
set JAVA_HOME=
set JTJG_DRIVE=
set JTJG_HOME=
exit