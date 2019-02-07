FROM nexus.condor.com.br/base/oracle-jdk:8u192

ENV JAVA_OPTS="-Xms128m -Xmx512m -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=124m -Djava.net.preferIPv4Stack=true -Djboss.modules.system.pkgs=org.jboss.byteman -Djava.awt.headless=true -XX:ParallelGCThreads=3 -XX:ConcGCThreads=3 -Djava.util.concurrent.ForkJoinPool.common.parallelism=3 -Duser.language=pt -Duser.country=BR"

ARG CONFIG

ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar ${CONFIG}

ADD app.jar app.jar