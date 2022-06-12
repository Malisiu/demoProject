package pl.coderslab.category;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean isAchivedWord;
    private Boolean isAchivedSentence;
    private Boolean isBasic;


    public Boolean getBasic() {
        return isBasic;
    }

    public void setBasic(Boolean basic) {
        isBasic = basic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAchivedWord() {
        return isAchivedWord;
    }

    public void setAchivedWord(Boolean achivedWord) {
        isAchivedWord = achivedWord;
    }

    public Boolean getAchivedSentence() {
        return isAchivedSentence;
    }

    public void setAchivedSentence(Boolean achivedSentence) {
        isAchivedSentence = achivedSentence;
    }
}
