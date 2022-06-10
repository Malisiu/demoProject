package pl.coderslab.user_words;

import pl.coderslab.user.User;
import pl.coderslab.word.Word;

import javax.persistence.*;

@Entity
public class UserWords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userWordsId;

    @ManyToOne
    private User user;

    @ManyToOne
    private Word word;

    private Integer correctWord;

    private Integer wrongWord;

    private Integer correctSentence;

    private Integer wrongSentence;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUserWordsId() {
        return userWordsId;
    }

    public void setUserWordsId(Long userWordsId) {
        this.userWordsId = userWordsId;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Integer getCorrectWord() {
        return correctWord;
    }

    public void setCorrectWord(Integer correctWord) {
        this.correctWord = correctWord;
    }

    public Integer getWrongWord() {
        return wrongWord;
    }

    public void setWrongWord(Integer wrongWord) {
        this.wrongWord = wrongWord;
    }

    public Integer getCorrectSentence() {
        return correctSentence;
    }

    public void setCorrectSentence(Integer correctSentence) {
        this.correctSentence = correctSentence;
    }

    public Integer getWrongSentence() {
        return wrongSentence;
    }

    public void setWrongSentence(Integer wrongSentence) {
        this.wrongSentence = wrongSentence;
    }
}
