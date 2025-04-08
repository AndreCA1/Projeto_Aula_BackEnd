package ifmg.edu.br.Prj_BackEnd.entities;

import jakarta.persistence.*;

import javax.annotation.processing.Generated;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private double price;
    private String imageURL;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant createdAt;
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updatedAt;

    //Cada produto pode estar em mais categorias e cada categoria pode ter varios produtos, logo N pra N
    @ManyToMany

    //Cria a forma da table join pega o tipo dessa tabela, inverse da outra
    @JoinTable(name = "producty_category", joinColumns = @JoinColumn(name = "producty_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public Product() {

    }

    public Product(long id, String name, String description, double price, String imageURL) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageURL = imageURL;
    }

    public Product(Product entity) {
        this.id = entity.getId();
    }

    public Product(Product product, Set<Category> categories) {
        this(product);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
