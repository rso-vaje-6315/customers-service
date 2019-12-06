#!/bin/bash

set -e

# docker build -t gcr.io/${PROJECT_NAME}/${DOCKER_IMAGE_NAME}:$TRAVIS_COMMIT .

if [[ -z "${GCLOUD_PASS}" ]]; then
  echo "Google credentials not set!"
  exit 233
fi

echo $GCLOUD_PASS | base64 --decode -i > ${HOME}/gcloud-service-key.json
gcloud auth activate-service-account --key-file ${HOME}/gcloud-service-key.json

gcloud --quiet config set project $PROJECT_NAME
gcloud --quiet config set container/cluster $CLUSTER_NAME
gcloud --quiet config set compute/zone ${CLOUDSDK_COMPUTE_ZONE}
gcloud --quiet container clusters get-credentials $CLUSTER_NAME

# gcloud docker push gcr.io/${PROJECT_NAME}/${DOCKER_IMAGE_NAME}

# yes | gcloud beta container images add-tag gcr.io/${PROJECT_NAME}/${DOCKER_IMAGE_NAME}:$TRAVIS_COMMIT gcr.io/${PROJECT_NAME}/${DOCKER_IMAGE_NAME}:latest

kubectl config view
kubectl config current-context

# kubectl set image deployment/${KUBE_DEPLOYMENT_NAME} ${KUBE_DEPLOYMENT_CONTAINER}=rso6315/customers-service:latest -n e-store
kubectl rollout restart deployment/${KUBE_DEPLOYMENT_NAME} -n e-store
