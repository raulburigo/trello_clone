package org.raulburigo.rest;

import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.raulburigo.dto.CreateCategoryDto;
import org.raulburigo.service.CategoryService;

import io.quarkus.security.identity.SecurityIdentity;

@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/categories")
public class CategoryRest {

    @Inject
    CategoryService service;

    @Inject
    Validator validator;

    @Inject
    SecurityIdentity identity;
    
    @GET
    @Path("/")
    @PermitAll
    public Response listarCategorias() {
        return Response.status(Status.OK).entity(service.listAll()).build();
    }
    

    @POST
    @Path("/")
    @RolesAllowed("admin")
    public Response criarCategoria(@RequestBody CreateCategoryDto dto) {
        Set<ConstraintViolation<CreateCategoryDto>> violations
        = validator.validate(dto);
        if (violations.isEmpty())
            return Response
                .status(Status.CREATED)
                .entity(service.createCategory(dto))
                .build();
        else
            return Response
                .status(Status.BAD_REQUEST)
                .entity(violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList())
                )
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
            .entity("Categoria excluída com sucesso")
            .build();
    }
        
}
