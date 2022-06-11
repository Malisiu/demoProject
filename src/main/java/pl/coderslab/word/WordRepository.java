package pl.coderslab.word;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.category.Category;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word,Long> {

    List<Word> findAllByCategory_Id(long id);

    @Query("select w.category.id from Word w where w.id = ?1")
    Long findCategoryId(Long id);

}
