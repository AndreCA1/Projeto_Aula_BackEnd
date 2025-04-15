package ifmg.edu.br.Prj_BackEnd.dtos;

import ifmg.edu.br.Prj_BackEnd.entities.Category;
import ifmg.edu.br.Prj_BackEnd.entities.Product;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ProductDTO {

    private long id;
    private String name;
    private String description;
    private double price;
    private String imageURL;

    private Set<CategoryDTO> categories = new HashSet<>();

    public ProductDTO() {}

    public ProductDTO(long id, String name, String description, double price, String imageURL) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageURL = imageURL;
    }

    //Converte produto em DTO
    public ProductDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.imageURL = entity.getImageURL();

        //pego cada categoria recebida (tipo entity) e passo ela para o construtor categoryDTO (recebe uma entidade e converte em DTO)
        entity.getCategories().forEach(category -> this.categories.add(new CategoryDTO(category)));
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<CategoryDTO> getCategories() {
        return categories;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProductDTO product)) return false;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imageURL='" + imageURL + '\'' +
                ", categories=" + categories +
                '}';
    }
}
