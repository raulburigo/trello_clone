package org.raulburigo.rest;

import java.util.InputMismatchException;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.raulburigo.dto.CreateTodoDto;
import org.raulburigo.dto.UpdateTodoDto;
import org.raulburigo.models.Todo;
import org.raulburigo.service.TodoService;

import io.quarkus.security.identity.SecurityIdentity;


@Path("/todos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TodoRest {
    
    @Inject
    TodoService service;

    @Inject
    SecurityIdentity identity;

    @POST
    @Path("/")
    // @RolesAllowed("user")
    @Operation(
        summary = "Create new Todo",
        description = "Creates a new Todo in the Todo List."
    )
    public Response adicionarTodo(CreateTodoDto newTodo) {
        Todo response;
        try {
            response = service.criarTodo(newTodo);
            return Response
                .status(Status.CREATED)
                .entity(response)
                .build();
        } catch (NullPointerException e) {
            return Response
                .status(Status.BAD_REQUEST)
                .entity("Title can't be null")
                .build();
        } catch (InputMismatchException e) {
            return Response
                .status(Status.BAD_REQUEST)
                .entity("Invalid input")
                .build();
        }
    }

    @GET
    @Path("/")
    public Response listarTodos() {
        return Response
            .status(Status.OK)
            .entity(service.listarTodos())
            .build();
    }
    
    @GET
    @Path("/{id}")
    public Response buscarId(@PathParam("id") long id) {
        return Response
            .status(Status.OK)
            .entity(service.buscarId(id))
            .build();
    }
    
    @DELETE
    @Path("/{id}/delete")
    public Response deletarId(@PathParam("id") long id) {
        service.deleteById(id);
        return Response
            .status(Status.ACCEPTED)
            .entity("{'message': 'Todo excluído com sucesso'}")
            .build();
    }
    
    @PUT
    @Path("/{id}/update")
    public Response alterarTodo(@PathParam("id") long id, UpdateTodoDto updateDto) {
        return Response
            .status(Status.OK)
            .entity(service.alterarTodo(id, updateDto))
            .build();
    }

}
