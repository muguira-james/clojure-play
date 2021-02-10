FROM     clojure
RUN      mkdir -p /usr/src/app
WORKDIR  /usr/src/app

COPY     . /usr/src/app
RUN      mv "$(lein ring uberjar | sed -n 's/^Created \(.*standalone\.jar\)/\1/p')" app-standalone.jar
CMD      ["java", "-jar", "app-standalone.jar"]

#
# a simple way to create a running container is
#
# FROM clojure
# mkdir -p /clojure
# WORKDIR /clojure
# COPY . /clojure
# CMD [ "lein", "ring", "server-headless" ]
