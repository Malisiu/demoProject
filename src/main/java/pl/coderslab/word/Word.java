package pl.coderslab.word;

import pl.coderslab.category.Category;

import javax.persistence.*;

@Entity
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String wordPl;
    private String wordEn;
    private String sentencePl;
    private String sentenceEn;
    @ManyToOne
    private Category category;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWordPl() {
        return wordPl;
    }

    public void setWordPl(String wordPl) {
        this.wordPl = wordPl;
    }

    public String getWordEn() {
        return wordEn;
    }

    public void setWordEn(String wordEn) {
        this.wordEn = wordEn;
    }

    public String getSentencePl() {
        return sentencePl;
    }

    public void setSentencePl(String sentencePl) {
        this.sentencePl = sentencePl;
    }

    public String getSentenceEn() {
        return sentenceEn;
    }

    public void setSentenceEn(String sentenceEn) {
        this.sentenceEn = sentenceEn;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
