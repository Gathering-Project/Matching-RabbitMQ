FROM --platform=linux/arm64 openjdk:17-jdk-slim
WORKDIR /app

# 빌더 이미지에서 jar 파일만 복사
COPY /build/libs/*-SNAPSHOT.jar ./app.jar
CMD ["java", "-jar", "app.jar"]

EXPOSE 8080


