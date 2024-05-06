# docker build -t battleship .
# docker run -ti battleship

FROM sbtscala/scala-sbt:eclipse-temurin-jammy-17.0.10_7_1.9.9_3.4.1

RUN apt-get update

EXPOSE 8080


# work and local directory
WORKDIR /app
ADD model /app/model
ADD modules/core /app/controller
#ADD gui /app/gui
ADD modules/tui /app/tui
ADD util /app/util
ADD modules/persistency /app/persistency
ADD src /app/src

ADD build.sbt /app/build.sbt
ADD project /app/project

# run sbt
#CMD cd /app && sbt run && "q" && cd /app/tui/src/main/scala/aview && sbt run