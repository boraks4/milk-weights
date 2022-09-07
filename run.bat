set PATH_TO_FX=C:\Users\Sean\Documents\JavaFX
javac --module-path %PATH_TO_FX%;%PATH_TO_FX%\lib --add-modules javafx.controls,javafx.fxml .\application\*.java
java --module-path %PATH_TO_FX%;%PATH_TO_FX%\lib --add-modules javafx.controls,javafx.fxml .\application\Main.java
pause