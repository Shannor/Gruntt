package comic.shannortrotty.gruntt.classes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shannor on 8/18/2017.
 */

public class Comic implements ComicInterface {

    @SerializedName("title")
    private String title;
    @SerializedName("link")
    private String link;
    @SerializedName("category")
    private String category;

    public Comic(String title, String link, String category) {
        this.title = title;
        this.link = link;
        this.category = category;
    }

    public Comic() {
        this.title = "";
        this.link = "";
        this.category = "";
    }

    @Override
    public String getFormattedURL() {
        String[] correctLink = link.split("/");
        return correctLink[(correctLink.length - 1)];
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Comic{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
