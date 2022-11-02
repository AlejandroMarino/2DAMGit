set path=%path%;C:\tools\jdk-17.0.2\bin

javac Dungeon.java
jar -cfvn Dungeon.jar Manifest.txt *.class
