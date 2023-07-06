#!/bin/bash

# Specify the Docker container ID
container_id="dd54c309f8e0ba91fd9093459138f2650b52729888b79ea65c7e4fff09afb665"

# Specify the realm name
current_date=$(date +%Y-%m-%d_%H-%M-%S)
realm_name="Order-System"


# Specify the local directory to copy the exported file to
local_dir="./realm-config/$current_date/"

# Execute the export command inside the Docker container
docker exec -it $container_id bash -c "/opt/keycloak/bin/kc.sh export --dir /opt/keycloak/data/import --users realm_file --realm $realm_name"

# Copy the exported file from the Docker container to the local directory
docker cp $container_id:/opt/keycloak/data/import/$realm_name-realm.json $local_dir
