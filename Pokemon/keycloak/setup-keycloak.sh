#!/bin/bash

# Wait for Keycloak to start
until $(curl --output /dev/null --silent --head --fail http://localhost:8080); do
    printf '.'
    sleep 5
done

# Login to Keycloak
/opt/bitnami/keycloak/bin/kcadm.sh config credentials --server http://localhost:8080 --realm master --user $KEYCLOAK_ADMIN --password $KEYCLOAK_ADMIN_PASSWORD

# Create a new realm
/opt/bitnami/keycloak/bin/kcadm.sh create realms -s realm=EA -s enabled=true

# Create a new client with Direct Access Grants enabled
/opt/bitnami/keycloak/bin/kcadm.sh create clients -r EA -s clientId=web-app -s enabled=true -s 'redirectUris=["http://localhost:8090/*"]' -s publicClient=false -s secret=123456 -s directAccessGrantsEnabled=true

# Retrieve the client ID of the web-app client
CLIENT_ID=$(/opt/bitnami/keycloak/bin/kcadm.sh get clients -r EA -q clientId=web-app --fields id --format csv | tail -n 1 | tr -d '\r')

# Create a new role in the web-app client
/opt/bitnami/keycloak/bin/kcadm.sh create clients/$CLIENT_ID/roles -r EA -s name=ROLE_SUPERADMIN

# Create a new user
/opt/bitnami/keycloak/bin/kcadm.sh create users -r EA -s username=superadmin -s enabled=true -s emailVerified=true -s firstName=Super -s lastName=Admin -s email=superadmin@example.com

# Set password for the new user and ensure it is not temporary
/opt/bitnami/keycloak/bin/kcadm.sh set-password -r EA --username superadmin --new-password 1234 --temporary=false

# Assign the role to the new user
/opt/bitnami/keycloak/bin/kcadm.sh add-roles -r EA --uusername superadmin --cclientid web-app --rolename ROLE_SUPERADMIN
