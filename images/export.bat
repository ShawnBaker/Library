@echo off

call :export_icon app_icon
goto :eof

:export
REM inkscape -f images.svg -i %~1 -j -d 67.5 -e ..\app\src\main\res\drawable-ldpi\%~1.png
inkscape -f images.svg -i %~1 -j -d 90 -e ..\app\src\main\res\drawable-mdpi\%~1.png
inkscape -f images.svg -i %~1 -j -d 135 -e ..\app\src\main\res\drawable-hdpi\%~1.png
inkscape -f images.svg -i %~1 -j -d 180 -e ..\app\src\main\res\drawable-xhdpi\%~1.png
inkscape -f images.svg -i %~1 -j -d 270 -e ..\app\src\main\res\drawable-xxhdpi\%~1.png
inkscape -f images.svg -i %~1 -j -d 360 -e ..\app\src\main\res\drawable-xxxhdpi\%~1.png
goto :eof

:export_icon
REM inkscape -f images.svg -i %~1 -j -d 67.5 -e ..\app\src\main\res\mipmap-mdpi\ic_launcher.png
inkscape -f images.svg -i %~1 -j -d 90 -e ..\app\src\main\res\mipmap-mdpi\ic_launcher.png
inkscape -f images.svg -i %~1 -j -d 135 -e ..\app\src\main\res\mipmap-hdpi\ic_launcher.png
inkscape -f images.svg -i %~1 -j -d 180 -e ..\app\src\main\res\mipmap-xhdpi\ic_launcher.png
inkscape -f images.svg -i %~1 -j -d 270 -e ..\app\src\main\res\mipmap-xxhdpi\ic_launcher.png
inkscape -f images.svg -i %~1 -j -d 360 -e ..\app\src\main\res\mipmap-xxxhdpi\ic_launcher.png
goto :eof
