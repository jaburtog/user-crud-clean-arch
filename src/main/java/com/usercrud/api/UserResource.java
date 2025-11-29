package com.usercrud.api;

import com.usercrud.domain.User;
import com.usercrud.exception.UserNotFoundException;
import com.usercrud.exception.UsernameAlreadyExistsException;
import com.usercrud.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

/**
 * REST API resource for User CRUD operations.
 * This class exposes RESTful endpoints for user management.
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    private UserService userService;

    /**
     * Creates a new user.
     *
     * @param user the user to create
     * @return Response with created user and 201 status, 400 if validation fails, or 409 if username exists
     */
    @POST
    public Response createUser(User user) {
        try {
            User createdUser = userService.createUser(user);
            return Response.status(Response.Status.CREATED).entity(createdUser).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage())).build();
        } catch (UsernameAlreadyExistsException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    /**
     * Gets a user by id.
     *
     * @param id the user id
     * @return Response with user and 200 status, or 404 if not found
     */
    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return Response.ok(user.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("User not found with id: " + id)).build();
        }
    }

    /**
     * Gets all users.
     *
     * @return Response with list of all users and 200 status
     */
    @GET
    public Response getAllUsers() {
        List<User> users = userService.getAllUsers();
        return Response.ok(users).build();
    }

    /**
     * Updates an existing user.
     *
     * @param id   the user id
     * @param user the user data to update
     * @return Response with updated user and 200 status, 404 if not found, or 409 if username exists
     */
    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") Long id, User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            return Response.ok(updatedUser).build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage())).build();
        } catch (UsernameAlreadyExistsException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    /**
     * Deletes a user by id.
     *
     * @param id the user id
     * @return Response with 204 status if successful, or 404 if not found
     */
    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        try {
            userService.deleteUser(id);
            return Response.noContent().build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    /**
     * Simple error response class.
     */
    public static class ErrorResponse {
        private String message;

        public ErrorResponse() {
        }

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
