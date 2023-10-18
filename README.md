# score-tracker

#### Setup mysql for development mode
- Nếu không dùng docker mà chỉ chạy trên máy cá nhân thì phải thực hiện 2 bước sau
  - Bước 1: cài đặt maven và build source code thành file jar (chạy lệnh ```mvn clean install``` để build file .jar)
  - Bước 2: cài đặt môi trường để chạy java(jdk) để run file jar (chạy lệnh ```java -jar score-tracker.jar``` để run file .jar)

- Sử dụng docker: Tạo Dockerfile, chia làm 2 giai đoạn để build image
  - Giai đoạn 1: từ image maven, copy source vào /app và chạy lệnh ```mvn clean install```
  - Giai đoạn 2: sau khi build được file jar từ giai đoạn 1, từ image jdk chạy ```java -jar score-tracker.jar```
  - Chạy lệnh ```docker build -t score-tracker:1.0 .``` để build image, trong đó -t (tag) để khai báo tên image và version của image, trong trường hợp này tên image là score-tracker và version là 1.0
  
- Chạy lệnh ```docker images``` để check image vừa được tạo
#### Dockerfile
```dockerfile
FROM maven:3.8.3-openjdk-17 as build
WORKDIR /app
COPY . .
RUN mvn clean install

FROM openjdk:17-alpine
COPY --from=build /app/target/score-tracker-0.0.1-SNAPSHOT.jar score-tracker.jar
EXPOSE 8080
ENTRYPOINT exec java -jar score-tracker.jar
```

- Chạy tiếp lệnh ```docker-compose up``` để start database và ứng dụng spring