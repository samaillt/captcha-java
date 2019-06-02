cd ./CAPTCHA && find . -name "*.java"  -print | xargs javac -d classes && rsync -avz --exclude '*.java' ./sources/ ./classes/ && java -cp classes fr.upem.captcha.ui.MainUi
