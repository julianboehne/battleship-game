FROM hseeberger/scala-sbt:8u222_1.3.5_2.13.1
ENV DISPLAY=host.docker.internal:0.0

# update and intsall openjfx && sbt
RUN apt-get update && \
    apt-get install -y --no-install-recommends openjfx && \
    rm -rf /var/lib/apt/lists/* && \
    apt-get install -y sbt libxrender1 libxtst6 libxi6

# work directory and local file combine
WORKDIR /battleship-game
ADD . /battleship-game

# run sbt
CMD sbt run