package org.raulburigo.rest;

import java.util.InputMismatchException;

import javax.inject.Inject;
import javax.transaction.Transactional;
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
import org.jboss.logging.Logger;
import org.raulburigo.models.Category;
import org.raulburigo.models.Todo;
import org.raulburigo.service.TodoService;


@Path("/todos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TodoRest {
    
    @Inject
    TodoService service;

    @POST
    @Path("/")
    @Operation(
        summary = "Create new Todo",
        description = "Creates a new Todo in the Todo List."
    )
    public Response adicionarTodo(Todo newTodo) {
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
            .entity("todo excluído com sucesso")
            .build();
    }
    
    @PUT
    @Path("/{id}/update")
    public Response alterarTodo(@PathParam("id") long id, Todo updatedTodo) {
        return Response
            .status(Status.OK)
            .entity(service.alterarTodo(id, updatedTodo))
            .build();
    }

    @Transactional
    @GET
    @Path("/{categoryId}/{todoId}")
    public Response moverTodo(@PathParam("categoryId") Long categoryId, @PathParam("todoId") Long todoId) {
        Todo todo = service.buscarId(todoId);
        Category cat = Category.findById(categoryId);
        cat.persist();
        todo.setCategory(cat);
        todo.persist();
        return Response
            .status(Status.OK)
            .entity(todo)
            .build();
    }


    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        Logger.getLogger(TodoRest.class).info("hello");
        return "hello";
    }
    
}

    // @GET
    // @Path("/completed")
    // public Response listarCompletos() {
    //     return Response
    //         .status(Status.OK)
    //         .entity(service.listarCompletas())
    //         .build();
    // }

    // @GET
    // @Path("/page")
    // public Response listarPaginado() {
    //     return Response
    //         .status(Status.OK)
    //         .entity(service.pagination())
    //         .build();
    // }    
    
