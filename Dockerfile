#
# Provides the build container for this project. Derived from the base Dockerfile in the ingestion-utils repo.
#
FROM hyppo-build

COPY . /app/

RUN /usr/local/sbt/bin/sbt packageBin
