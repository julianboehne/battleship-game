# docker build -t persistence .

FROM sbtscala/scala-sbt:eclipse-temurin-jammy-17.0.10_7_1.9.9_3.4.1

RUN apt-get update

EXPOSE 8081

# work and local directory
WORKDIR /app
ADD . /app

# run sbt
CMD sbt run