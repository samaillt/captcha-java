# Captcha using Java

Project working fine on Ubuntu - Windows - MacOS

## Compile & Launch the app

From the git project root: 

```
cd ./CAPTCHA && rsync -avz --exclude '.java' ./sources/ ./classes/ && find . -name ".java" -print | xargs javac -d classes && java -cp classes fr.upem.captcha.ui.MainUi
```

*Noélie Bravo*, *Tom SAMAILLE*

IMAC engineering school - 2019
