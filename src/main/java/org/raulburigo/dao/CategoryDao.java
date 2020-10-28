package org.raulburigo.dao;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import org.eclipse.microprofile.opentracing.Traced;
import org.raulburigo.dto.CreateCategoryDto;
import org.raulburigo.models.Category;

@RequestScoped
@Traced
public class CategoryDao {

    @PersistenceContext
    EntityManager em;

    public List<Category> listAll() {
        return Category.listAll();
    }

    @Transactional
    public Category createCategory(CreateCategoryDto dto) {
        Category cat = new Category();
        cat.setName(dto.getName());
        cat.persist();
        return cat;
    }

    public Category buscarId(long catId) {
        Optional<Category> optional = Category.findByIdOptional(catId);
        Category cat = optional.orElseThrow(() -> new NotFoundException());
        return cat;
    }
    
    @Transactional
    public void deleteById(long catId) {
        Category cat = buscarId(catId);
        if (cat.isPersistent())
        cat.delete();
    }

    String texto = "good";
    char[] meuArray = {'g', 'o', 'o', 'd'};
    char ch = 'e';
    

}
