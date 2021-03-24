package xuanhieu.kafka.repository;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xuanhieu.kafka.entity.Products;

public interface ProductsRepository extends JpaRepository<Products,Integer> {

    @Query("SELECT pd FROM Products pd WHERE pd.nameProduct=:name")
    Optional<List<Products>>findProductsByNameProduct(@Param("name")String name);
}
