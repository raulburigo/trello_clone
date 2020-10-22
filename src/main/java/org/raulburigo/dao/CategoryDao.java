package org.raulburigo.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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

}
