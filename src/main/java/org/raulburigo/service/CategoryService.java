package org.raulburigo.service;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.opentracing.Traced;
import org.raulburigo.dao.CategoryDao;
import org.raulburigo.dto.CreateCategoryDto;
import org.raulburigo.models.Category;


@Traced
@RequestScoped
public class CategoryService {
    
    @Inject
    CategoryDao dao;

    public List<Category> listAll() {
        return dao.listAll();
    }

    public Category createCategory(CreateCategoryDto dto) {
        return dao.createCategory(dto);
    }

}
