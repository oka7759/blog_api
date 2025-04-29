FROM openjdk:23-jdk-slim
ENV TZ=Asia/Seoul
COPY build/libs/blog-0.0.1-SNAPSHOT.jar /portpolio_api.jar

#EXPOSE 8081
CMD ["java", "-jar", "/portpolio_api.jar"]
