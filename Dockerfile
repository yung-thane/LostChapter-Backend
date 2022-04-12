FROM gradle as build
WORKDIR /app
COPY build.gradle ./
RUN gradle clean build --no-daemon > /dev/null 2>&1 || true
COPY ./ /app
RUN gradle clean build --no-daemon --stacktrace

FROM openjdk:11
COPY --from=build /app/build/libs/lost-chapter-backend-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8081
ENTRYPOINT exec java -jar app.jar