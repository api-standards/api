#!/usr/bin/env bash
# Builds the container image with Jib and deploys it to Cloud Run.
#
# The Kotlin Toolchain has no Jib plugin, so this packages an executable jar
# and hands it to the Jib CLI (https://github.com/GoogleContainerTools/jib/tree/master/jib-cli).
#
# Requirements: `jib` on PATH (or JIB=/path/to/jib), and `gcloud` authenticated
# with `gcloud auth configure-docker ${REGION}-docker.pkg.dev` done once.
set -euo pipefail

PROJECT="${PROJECT:-api-standards}"
SERVICE="${SERVICE:-api-standards}"
REGION="${REGION:-europe-west9}"
REPOSITORY="${REPOSITORY:-api-standards}"
TAG="${TAG:-$(git rev-parse --short HEAD)}"
JIB="${JIB:-jib}"

IMAGE="${REGION}-docker.pkg.dev/${PROJECT}/${REPOSITORY}/${SERVICE}"
JAR="build/tasks/_api_standards_api_executableJarJvm/api_standards_api-jvm-executable.jar"

cd "$(dirname "$0")/.."

./kotlin package -f executable-jar

"$JIB" jar \
  --from=eclipse-temurin:25-jre \
  --target="${IMAGE}:${TAG}" \
  --additional-tags=latest \
  --expose=8080 \
  "$JAR"

gcloud run deploy "$SERVICE" \
  --project="$PROJECT" \
  --region="$REGION" \
  --image="${IMAGE}:${TAG}" \
  --quiet
