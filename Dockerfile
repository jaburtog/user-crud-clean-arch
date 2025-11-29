# Use Open Liberty as base image
FROM icr.io/appcafe/open-liberty:full-java17-openj9-ubi

# Copy PostgreSQL JDBC driver
COPY --chown=1001:0 postgresql-42.6.0.jar /opt/ol/wlp/usr/shared/resources/postgresql/

# Copy server configuration
COPY --chown=1001:0 src/main/liberty/config/server.xml /config/

# Copy application WAR
COPY --chown=1001:0 target/user-crud.war /config/apps/

# Configure Liberty
RUN configure.sh

# Expose ports
EXPOSE 9080 9443

# Start Liberty server
CMD ["/opt/ol/wlp/bin/server", "run", "defaultServer"]
