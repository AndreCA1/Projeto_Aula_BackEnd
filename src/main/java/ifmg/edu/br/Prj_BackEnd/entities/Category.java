package ifmg.edu.br.Prj_BackEnd.entities;

import ifmg.edu.br.Prj_BackEnd.dtos.CategoryDTO;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

//Mostra pro java que terá uma tabela no banco dessa classe
@Entity
@Table(name = "category")
public class Category {
    @Id
    // .Identity indica que é autogenerate, tem o .UUID quando é chave hash (String)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    //Apenas para alguns bancos de dados, mysql n precisa
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant createdAt;
    //Apenas para alguns bancos de dados, mysql n precisa
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updatedAt;

    //Relacionamento bidirecional
    //Todos os produtos daquela categoria, LAZY faz o java buscar esses dados no Banco apenas quando for chamado .products (EAGER ja tras direto)
    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private Set<Product> products = new HashSet<>();

    public Category() {}

    public Category(long id, String name) {
        this.name = name;
        this.id = id;
    }

    public Category(CategoryDTO dto) {
        this.id = dto.getId();
        this.name = dto.getName();
    }

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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    //O usuario não ira mexer nas variaveis pre Persist e Update

    //Chama esse metodo sempre que o objeto for criado
    @PrePersist
    private void prePersist() {
        //pega a hora atual
        createdAt = Instant.now();
    }

    //Chama esse metodo sempre que o objeto for alterado
    @PreUpdate
    private void preUpdate() {
        updatedAt = Instant.now();
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
