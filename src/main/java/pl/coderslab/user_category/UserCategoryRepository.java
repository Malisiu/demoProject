package pl.coderslab.user_category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserCategoryRepository extends JpaRepository<UserCategory,Long> {

    @Transactional
    @Modifying
    @Query("delete from UserCategory uc where uc.category.id = ?1")
    void deleteByCategoryId(Long id);

}
