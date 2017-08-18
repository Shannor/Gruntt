package comic.shannortrotty.gruntt.classes;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * Created by shannortrotty on 2/3/17.
 * Calls to hold the return call for Popular Comics Format.
 * Differs slightly from other ComicInterface calls
 * Used in the retrofit calls
 */

public class PopularComic implements ComicInterface {

    @SerializedName("title")
    private String title;
    @SerializedName("link")
    private String link;
    @SerializedName("img")
    private String thumbnailUrl;
    @SerializedName("issueCount")
    private int issueCount;

    public PopularComic(){
        this.title = null;
        this.link = null;
        this.thumbnailUrl = null;
        this.issueCount = 0;
    }

    public PopularComic(String title, String link, String thumbnailUrl, int issueCount){
        this.title = title;
        this.link = link;
        this.thumbnailUrl = thumbnailUrl;
        this.issueCount = issueCount;
    }
    public PopularComic(String title, String link, String thumbnailUrl){
        this.title = title;
        this.link = link;
        this.thumbnailUrl = thumbnailUrl;
        this.issueCount = 0;
    }

    //Getters and Setters
    public String getTitle() {
        return title;
    }

    @Override
    public String getFormattedURL() {
        return getURLFormattedLink();
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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getURLFormattedLink(){
        String[] correctLink = getLink().split("/");
        return correctLink[(correctLink.length - 1)];
    }

    public int getIssueCount() {
        return issueCount;
    }

    public void setIssueCount(int issueCount) {
        this.issueCount = issueCount;
    }

    @Override
    public boolean equals(Object obj) {
        // self check
        if (this == obj)
            return true;
        // null check
        if (obj == null)
            return false;
        // type check and cast
        if (getClass() != obj.getClass())
            return false;
        PopularComic popularComic = (PopularComic) obj;
        // field comparison
        return Objects.equals(this.title, popularComic.getTitle());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "ComicInterface{" +
                "title='" + title + '\'' +
                "link=" + link +
                '}';
    }
}
