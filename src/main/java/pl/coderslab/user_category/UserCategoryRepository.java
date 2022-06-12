package pl.coderslab.user_category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserCategoryRepository extends JpaRepository<UserCategory,Long> {

    @Transactional
    @Modifying
    @Query("delete from UserCategory uc where uc.category.id = ?1")
    void deleteByCategoryId(Long id);

    @Query("select uc from UserCategory uc where uc.isAchivedWord = true and uc.user.id = ?1")
    List<UserCategory> findByWordAchived(Long id);

    @Query("select uc from UserCategory uc where uc.isAchivedSentence = true and uc.user.id = ?1")
    List<UserCategory> findBySentenceAchived(Long id);

    @Query("select uc from UserCategory uc where uc.user.id = ?1 and uc.category.id = ?2")
    UserCategory findByUserIdAndCategoryId(Long userId,Long categoryId);

}
