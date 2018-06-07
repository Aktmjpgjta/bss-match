#/bin/bash
# Export environment variable
#source /etc/profile
# Start tomcat
bash /data/apache-tomcat-8.5.16/bin/catalina.sh start
#tail -f /data/test.txt
tail -f /data/apache-tomcat-8.5.16/logs/catalina.out
