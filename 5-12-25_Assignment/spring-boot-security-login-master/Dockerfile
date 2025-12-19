FROM maven:3.8.5-openjdk-17

WORKDIR /Users/anushreyshubham/eclipse-workspace/Chubb_Daily_Assignments/5-12-25_Assignment/spring-boot-security-login-master
COPY . .
RUN mvn clean install -DskipTests

CMD mvn spring-boot:run