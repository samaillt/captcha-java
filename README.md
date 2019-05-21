# Captcha using Java

Project working fine on Ubuntu - Windows - MacOS

## Launch the application

```
cd ./CAPTCHA && rsync -avz --exclude '.java' ./sources/ ./classes/ && find . -name ".java" -print | xargs javac -d classes && java -cp classes fr.upem.captcha.ui.MainUi
```

*Noélie Bravo*, *Tom SAMAILLE*

IMAC engineering school - 2019