#!/bin/bash
# MicroProfile Config reads env vars automatically (SONARR_API_KEY -> sonarr.api-key).
# This script is a simple passthrough with optional -Xmx tuning.
exec java \
  -Xmx100m \
  -Dquarkus.http.enabled=false \
  -jar /app/quarkus-run.jar
