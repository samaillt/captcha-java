# Captcha using Java

Project working fine on Ubuntu - Windows - MacOS

## Compile & Launch the app

From the git project root: 

```
cd ./CAPTCHA
```
```
find . -name "*.java"  -print | xargs javac -d classes
```
```
rsync -avz --exclude '*.java' ./sources/ ./classes/
```
```
java -cp classes fr.upem.captcha.ui.MainUi
```

or run 

```
./lauch.sh
```

*Noélie Bravo*, *Tom SAMAILLE*

IMAC engineering school - 2019
