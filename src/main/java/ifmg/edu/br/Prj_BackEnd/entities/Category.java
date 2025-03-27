package ifmg.edu.br.Prj_BackEnd.entities;

import jakarta.persistence.*;

import java.util.Objects;

//Mostra pro java que terá uma tabela no banco dessa classe
@Entity
@Table(name = "category")
public class Category {
    @Id
    // .Identity indica que é autogenerate, tem o .UUID quando é chave hash (String)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    public Category(long id, String name) {
        this.name = name;
        this.id = id;
    }

    public Category() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Category category)) return false;
        return id == category.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
