package pl.coderslab.user_category;

import pl.coderslab.category.Category;
import pl.coderslab.user.User;

import javax.persistence.*;

@Entity
public class UserCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;

    private boolean isAchivedWord;

    private boolean isAchivedSentence;

    public boolean isAchivedWord() {
        return isAchivedWord;
    }


    public void setAchivedWord(boolean achivedWord) {
        isAchivedWord = achivedWord;
    }

    public boolean isAchivedSentence() {
        return isAchivedSentence;
    }

    public void setAchivedSentence(boolean achivedSentence) {
        isAchivedSentence = achivedSentence;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
