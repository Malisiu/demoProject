package pl.coderslab.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query("select c from Category c where c.isBasic = true")
    List<Category> basicCategory();

    @Query("select c from Category c join UserCategory uc on c.id = uc.category.id where uc.user.id = ?1 and c.isBasic = false ")
    List<Category> userCategory(Long id);
    @Modifying
    @Transactional
    @Query("delete from Category c where c.id = ?1")
    void deleteById(Long id);


    @Query("select c from Category c where c.id = ?1")
    Category selectCat(Long id);



}
