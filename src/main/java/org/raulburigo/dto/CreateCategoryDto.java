package org.raulburigo.dto;

import javax.validation.constraints.NotNull;

public class CreateCategoryDto {
    
    @NotNull(message = "Nome é obrigatório")
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
