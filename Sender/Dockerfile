FROM openjdk:17
WORKDIR /app
COPY src/sensor.csv /app
ADD target/DS_2022_30244_Zilai_Denis_Assignment_2_Sender.jar DS_2022_30244_Zilai_Denis_Assignment_2_Sender.jar
EXPOSE 80:80
ENTRYPOINT ["java", "-jar", "DS_2022_30244_Zilai_Denis_Assignment_2_Sender.jar"]