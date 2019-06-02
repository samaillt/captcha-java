cd ./CAPTCHA && rsync -avz --exclude '*.java' ./sources/ ./classes/ && find . -name "*.java"  -print | xargs javac -d classes && java -cp classes fr.upem.captcha.ui.MainUi
