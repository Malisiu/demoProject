package pl.coderslab.word;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.category.Category;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word,Long> {

    List<Word> findAllByCategory_Id(long id);



}
