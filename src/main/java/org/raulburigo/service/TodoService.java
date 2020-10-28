package org.raulburigo.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import org.eclipse.microprofile.opentracing.Traced;
import org.raulburigo.dto.CreateTodoDto;
import org.raulburigo.dto.UpdateTodoDto;
import org.raulburigo.models.Activity;
import org.raulburigo.models.ActivityType;
import org.raulburigo.models.Category;
import org.raulburigo.models.Todo;

import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Sort.Direction;
import io.quarkus.security.identity.SecurityIdentity;

@RequestScoped
@Traced
public class TodoService {
    

    @Inject
    SecurityIdentity identity;
    

    public List<Todo> listarTodos() {
        List<Todo> todos = Todo.listAll(Sort.by("id"));
        return todos;
    }
    
    @Transactional
    public Todo criarTodo(CreateTodoDto todoDto) {
        if (todoDto.getTitle() == null || todoDto.getCategoryId() == null)
        throw new NullPointerException();
        Todo todo = new Todo();
        Category cat = Category.findById(todoDto.getCategoryId());
        todo.setCategory(cat);
        todo.setTitle(todoDto.getTitle());
        todo.setPosition(Long.valueOf(cat.todos.size()));
        todo.setCompleted(false);
        todo.persist();
        criarAtividade(todo, identity.getPrincipal().getName(), ActivityType.CREATED);
        return todo;
    }
    
    public Todo buscarId(long todoId) {
        Optional<Todo> optional = Todo.findByIdOptional(todoId);
        Todo todo = optional.orElseThrow(() -> new NotFoundException());
        return todo;
    }
    
    @Transactional
    public void deleteById(long todoId) {
        Todo todo = buscarId(todoId);
        todo.delete();

    }
    
    @Transactional
    public Todo alterarTodo(long todoId, UpdateTodoDto dto) {
        Todo todo = buscarId(todoId);
        if (dto.getCategoryId() != null) {
            Category oldCategory = Category.findById(todo.getCategory().id);
            todo.setCategory(Category.findById(dto.getCategoryId()));
            if (!todo.getCategory().equals(oldCategory))
                resortTodos(oldCategory);
        }
        if (dto.getTitle() != null)
            todo.setTitle(dto.getTitle());
        if (dto.isCompleted() != null)
            todo.setCompleted(dto.isCompleted());
            criarAtividade(todo, identity.getPrincipal().getName(), ActivityType.COMPLETED);
        if (dto.getPosition() != null
            && (!dto.getPosition().equals(todo.getPosition())
                || dto.getCategoryId() != null)) {
            todo.setPosition(dto.getPosition());
            resortTodos(todo.getCategory());
        }
        return todo;
    }
            
    private void resortTodos(Category categoryToSort) {
        AtomicInteger counter = new AtomicInteger();
        List<Todo> todos = Todo
            .list("category", Sort
                .by("position").ascending()
                .and("updatedAt", Direction.Descending)
                    , categoryToSort);
            todos.forEach(
                item -> {
                    item.setPosition(counter.longValue());
                    counter.getAndIncrement();
                }
            );
    }

    @Transactional
    private void criarAtividade(Todo todo, String username, ActivityType type) {
        Activity activity = new Activity(todo, username, type);
        activity.persist();
    }

    
}
