package ifmg.edu.br.Prj_BackEnd.dtos;

import ifmg.edu.br.Prj_BackEnd.entities.Product;
import ifmg.edu.br.Prj_BackEnd.projections.ProductProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.hateoas.RepresentationModel;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

//
public class ProductListDTO extends RepresentationModel<ProductListDTO>{

    @Schema(description = "Database generated ID product")
    private long id;
    @Schema(description = "Prosuct name")
    @Size(min = 3, max = 255, message = "Deve ter entre 3 e 255 caracteres")
    private String name;
    @Positive(message = "Preço deve ter valor positivo")
    private double price;
    @Schema(description = "Image URL")
    private String imageURL;

    public ProductListDTO() {}

    public ProductListDTO(long id, String name, String description, double price, String imageURL) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageURL = imageURL;
    }

    //Converte produto em DTO
    public ProductListDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.imageURL = entity.getImageURL();
    }

    public ProductListDTO(ProductProjection entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.imageURL = entity.getImageUrl();
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


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProductListDTO product)) return false;
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
                ", price=" + price +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
