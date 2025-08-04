#!/bin/bash

# Start Keycloak in the background
/opt/bitnami/keycloak/bin/kc.sh start-dev &

# Wait for Keycloak to be ready
#/opt/bitnami/scripts/setup-keycloak.sh

# Keep the container running
tail -f /dev/null
