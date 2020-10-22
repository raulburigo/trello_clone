package org.raulburigo.service;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

// import org.raulburigo.models.Category;
import org.raulburigo.models.Todo;

import io.quarkus.panache.common.Sort;

@RequestScoped
public class TodoService {
    
    public List<Todo> listarTodos() {
        List<Todo> todos = Todo.listAll(Sort.by("id"));
        return todos;
    }
    
    public Todo buscarId(long todoId) {
        Optional<Todo> optional = Todo.findByIdOptional(todoId);
        Todo todo = optional.orElseThrow(() -> new NotFoundException());
        return todo;
    }
    
    @Transactional
    public Todo criarTodo(Todo todo) {
        if (todo.getTitle() == null)
            throw new NullPointerException();
        if (todo.getId() != null || todo.getCreatedAt() != null || todo.getUpdatedAt() != null)
            throw new InputMismatchException();
        todo.setCompleted(false);
        todo.persist();
        return todo;
    }
    
    @Transactional
    public void deleteById(long todoId) {
        Todo todo = buscarId(todoId);
        if (todo.isPersistent())
        todo.delete();
    }
    
    @Transactional
    public Todo alterarTodo(long todoId, Todo updatedTodo) {
        Todo todo = buscarId(todoId);
        todo.setTitle(updatedTodo.getTitle());
        todo.setCompleted(updatedTodo.isCompleted());
        todo.persist();
        return todo;
    }

    @Transactional
    public Todo alterarCategoria(long todoId, Todo updatedTodo) {
        Todo todo = buscarId(todoId);
        // Long id = 2L;
        // Category cat = Category.findById(id);
        // cat.persist();
        System.out.println(updatedTodo.getCategory());
        // todo.setCategory(cat);
        todo.setTitle(updatedTodo.getTitle());
        todo.setCompleted(updatedTodo.isCompleted());
        todo.persist();
        return todo;
    }
    
}
