package org.raulburigo.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Table(name = "categories")
public class Category extends PanacheEntity {

    @Column(nullable = false)
    private String name;

    @OneToMany(
        mappedBy = "category",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )

    public List<Todo> todos;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
