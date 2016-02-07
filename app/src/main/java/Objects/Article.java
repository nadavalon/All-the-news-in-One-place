package Objects;

/**
 * Created by Nadav Alon on 05/02/2016.
 */
public class Article {
    public final String title;
    public final String image;
    public String description;



    public Article(String title, String description, String image) {
        this.title = title;
        this.description = description;
        this.image = image;

    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }


}
