package comic.shannortrotty.gruntt.classes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shannortrotty on 3/19/17.
 */

public class SearchComic implements Comic {

    @SerializedName("title")
    private String title;
    @SerializedName("link")
    private String link;
    @SerializedName("img")
    private String thumbnailImg;
    @SerializedName("genre")
    private String genres;


    public SearchComic() {
        this.title = null;
        this.link = null;
        this.thumbnailImg = null;
        this.genres = null;
    }

    @Override
    public String getFormattedURL() {
        String[] correctLink = getLink().split("/");
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

    public String getThumbnailImg() {
        return thumbnailImg;
    }

    public void setThumbnailImg(String thumbnailImg) {
        this.thumbnailImg = thumbnailImg;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "SearchComic{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
