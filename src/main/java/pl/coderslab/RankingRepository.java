package pl.coderslab;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.user_words.UserWords;

import java.util.List;

@Repository
public interface RankingRepository extends JpaRepository<UserWords,Long> {

    @Query("select uw from UserWords uw where uw.user.id = ?1 and (uw.correctWord + uw.wrongWord) > 0 and uw.word.category.isBasic is true order by (uw.correctWord/(uw.correctWord + uw.wrongWord))")
    List<UserWords> findAllUserWords(Long userId);

}
