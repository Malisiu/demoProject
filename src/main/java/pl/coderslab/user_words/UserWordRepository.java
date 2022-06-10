package pl.coderslab.user_words;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserWordRepository extends JpaRepository<UserWords,Long> {

    @Modifying
    @Transactional
    @Query(value = "insert into user_words (user_id,word_id) values (?1,?2)",nativeQuery = true)
    void createUserWord(Long userId,Long wordId);

}
