FROM 10.10.20.117:5000/baseweb:V1
#COPY server.xml /data/apache-tomcat-8.5.16/conf/
COPY bss-match-sys-web.war /data/apache-tomcat-8.5.16/webapps/
EXPOSE 20881
ENV JAVA_OPTS "$JAVA_OPTS -server -Xmx4096m -Xms4096m -Xmn1536m -Xss128k -XX:SurvivorRatio=6 -XX:MaxPermSize=512m -XX:ParallelGCThreads=8 -XX:MaxTenuringThreshold=0 -XX:+UseConcMarkSweepGC -Djavax.servlet.request.encoding=UTF-8 -Dfile.encoding=UTF-8 -Duser.language=zh_CN -Dsun.jnu.encoding=UTF-8 -Dspring.profiles.active=prod"
CMD ["sh","/usr/local/run.sh"]
