package pl.coderslab.word;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.category.Category;
import pl.coderslab.user_words.UserWords;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word,Long> {

    List<Word> findAllByCategory_Id(long id);

    @Query("select w.category.id from Word w where w.id = ?1")
    Long findCategoryId(Long id);

    @Query("select w from Word w join Category c on c.id = w.category.id where c.isBasic = true ")
    List<Word> findAllBasicWords();

    @Query("select distinct w.category.id from Word w join UserWords uw on w.id = uw.word.id where uw.user.id = ?1 and w.wordPl is not null  and w.wordEn is not null")
    List<Long> findIdCategoryOwnWorda(Long id);


    @Query("select distinct w.category.id from Word w join UserWords uw on w.id = uw.word.id where uw.user.id = ?1 and w.sentencePl is not null  and w.sentenceEn is not null")
    List<Long> findIdCategoryOwnSentence(Long id);
}
