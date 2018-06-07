#/bin/bash
# Export environment variable
#source /etc/profile
# Start tomcat
#echo "10.150.20.64 smp.hctest.com" >> /etc/hosts
#echo "10.150.20.64 cas.hctest.com" >> /etc/hosts
#echo "10.150.20.65 esb.hctest.com" >> /etc/hosts
bash /data/apache-tomcat-8.5.16/bin/catalina.sh start
#tail -f /data/test.txt
#echo "10.150.20.64 smp.hctest.com" >> /etc/hosts
#echo "10.150.20.64 cas.hctest.com" >> /etc/hosts
#echo "10.150.20.65 esb.hctest.com" >> /etc/hosts
tail -f /data/apache-tomcat-8.5.16/logs/catalina.out
