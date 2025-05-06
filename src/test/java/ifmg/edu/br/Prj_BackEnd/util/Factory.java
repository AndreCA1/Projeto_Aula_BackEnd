package ifmg.edu.br.Prj_BackEnd.util;

import ifmg.edu.br.Prj_BackEnd.dtos.ProductDTO;
import ifmg.edu.br.Prj_BackEnd.entities.Category;
import ifmg.edu.br.Prj_BackEnd.entities.Product;

public class Factory {

    public static Product createProduct() {
        Product product = new Product();
        product.setName("Iphone");
        product.setDescription("Iphone 16 Pro Max krl a 4");
        product.setPrice(5000);
        product.setImageURL("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/25-big.jpg");
        product.getCategories().add(new Category(60L, "News"));
        return product;
    }

    public static ProductDTO createProductDTO(){
        Product p = createProduct();
        return new ProductDTO();
    }
}
