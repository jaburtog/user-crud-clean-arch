package com.usercrud.api;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * JAX-RS application configuration.
 * This class bootstraps the REST API at /api path.
 */
@ApplicationPath("/api")
public class UserApplication extends Application {
    // No additional configuration needed - all endpoints will be automatically discovered
}
