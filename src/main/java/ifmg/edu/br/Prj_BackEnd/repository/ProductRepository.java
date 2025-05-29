package ifmg.edu.br.Prj_BackEnd.repository;

import ifmg.edu.br.Prj_BackEnd.entities.Product;
import ifmg.edu.br.Prj_BackEnd.projections.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(nativeQuery = true,
        value = """
                    select * from
                    (select DISTINCT p.ID , p.NAME , p.IMAGEURL , p.PRICE
                    from PRODUCT p
                    inner Join PRODUCT_CATEGORY  pc on PRODUCT_ID = p.id
                    where (:categoriesID IS NULL || pc.CATEGORY_ID  in :categoriesID) and lower(p.name) like lower(CONCAT'%',:name,'%'))
                    ) as result
                """,
        countQuery = """
                        select count(*) from
                        (select DISTINCT p.ID , p.NAME , p.IMAGEURL , p.PRICE
                        from PRODUCT p
                        inner Join PRODUCT_CATEGORY  pc on PRODUCT_ID = p.id
                        where (:categoriesID IS NULL || pc.CATEGORY_ID  in :categoriesID) and lower(p.name) like lower(CONCAT'%',:name,'%'))
                        ) as result
                    """
    )
    public Page<ProductProjection> searchProducts(List<Long> categoriesID, String name, Pageable pageable);
}
