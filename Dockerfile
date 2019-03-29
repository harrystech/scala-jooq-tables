#
# Provides the build container for this project. Derived from the base Dockerfile in the ingestion-utils repo.
#
FROM hyppo-build

ARG JFROG_USERNAME
ARG JFROG_PASSWORD

ENV JFROG_USERNAME=${JFROG_USERNAME}
ENV JFROG_PASSWORD=${JFROG_PASSWORD}

COPY build.sbt /app/
COPY src/ /app/src/
COPY project/ /app/project/

RUN /usr/local/sbt/bin/sbt update 
RUN /usr/local/sbt/bin/sbt assembly
RUN /usr/local/sbt/bin/sbt publish
