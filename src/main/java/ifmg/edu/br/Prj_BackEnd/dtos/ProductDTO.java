package ifmg.edu.br.Prj_BackEnd.dtos;

import ifmg.edu.br.Prj_BackEnd.entities.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

//
public class ProductDTO extends RepresentationModel<ProductDTO>{

    @Schema(description = "Database generated ID product")
    private long id;
    @Schema(description = "Prosuct name")
    @Size(min = 3, max = 255, message = "Deve ter entre 3 e 255 caracteres")
    private String name;
    private String description;
    @Positive(message = "Preço deve ter valor positivo")
    private double price;
    private String imageURL;

    @Schema(description = "Product categories, one or more")
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
