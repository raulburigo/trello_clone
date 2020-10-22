package org.raulburigo.dto;

import java.util.List;

import org.raulburigo.models.Category;
import org.raulburigo.models.Todo;

public class CategoryDto {
    
    public String name;
    
    public List<Todo> todos;
    
    public CategoryDto(Long id) {

        Category cat = Category.findById(id);
        this.name = cat.getName();
        this.todos = Todo.list("category_id");
        
    }
    
}
