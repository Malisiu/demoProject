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
    @Query(value = "insert into user_words(user_id,word_id,correct_word,correct_sentence,wrong_word,wrong_sentence) values (?1,?2,0,0,0,0)",nativeQuery = true)
    void createUserWord(Long userId,Long wordId);

    @Modifying
    @Transactional
    @Query(value = "delete from UserWords uw where uw.word.id = ?1")
    void deleteUserWord(Long id);

    @Query("select uw from UserWords uw where uw.user.id = ?1 and uw.word.id = ?2")
    UserWords findByUserIdAndWordId(Long userId,Long wordId);
}
