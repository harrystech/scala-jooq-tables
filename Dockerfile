#
# Provides the run time container image for local testing of Hyppo Manager. Derived from the base Dockerfile in the ingestion-utils repo.
#
FROM hyppo-build

COPY . /app/

RUN /usr/local/sbt/bin/sbt packageBin
