FROM bitnami/keycloak:latest

ENV KEYCLOAK_ADMIN=admin
ENV KEYCLOAK_ADMIN_PASSWORD=123

# Expose the necessary ports
EXPOSE 8080

# Switch to root to install curl and set permissions
USER root

# Install curl
RUN install_packages curl

# Create the .keycloak directory with the correct permissions
RUN mkdir -p /home/keycloak/.keycloak && chown -R 1001:1001 /home/keycloak/.keycloak

# Copy the custom setup script and the startup script into the container
COPY setup-keycloak.sh /opt/bitnami/scripts/
COPY start-keycloak.sh /opt/bitnami/scripts/

# Make the scripts executable
RUN chmod +x /opt/bitnami/scripts/setup-keycloak.sh && chmod +x /opt/bitnami/scripts/start-keycloak.sh

# Switch back to the default user
USER 1001

# Start Keycloak using the startup script
ENTRYPOINT ["/opt/bitnami/scripts/start-keycloak.sh"]
