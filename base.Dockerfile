# install docker
# install Xming -> https://sourceforge.net/projects/xming/
# cd local_path/battleship-game
# docker build -f base.Dockerfile -t battleship-base .
# docker run -ti battleship


FROM hseeberger/scala-sbt:11.0.12_1.5.5_2.13.6
ENV DISPLAY=host.docker.internal:0.0


RUN apt-get update && \
    apt-get install -y --no-install-recommends openjfx && \
    apt-get install -y --no-install-recommends  libxrender1 libxtst6 libxi6 xorg x11-xserver-utils && \
    rm -rf /var/lib/apt/lists/*


# work and local directory --> without persistency
WORKDIR /app
ADD modules/core  /app/modules/core
ADD modules/tui  /app/modules/tui
ADD modules/gui  /app/modules/gui
ADD src /app/src

ADD project /app/project
ADD build.sbt /app/build.sbt

# run sbt
CMD sbt run
