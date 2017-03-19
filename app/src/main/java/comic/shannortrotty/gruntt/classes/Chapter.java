package comic.shannortrotty.gruntt.classes;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shannortrotty on 2/13/17.
 * Class created to hold Chapter information.
 */

public class Chapter implements Comic{
    @SerializedName("chapterName")
    private String chapterName;
    @SerializedName("link")
    private String link;
    @SerializedName("releaseDate")
    private String releaseDate;

    private Boolean haveRead;

    public Chapter(){
        this.chapterName = "-";
        this.releaseDate = "-";
        this.link = "-";
        this.haveRead = false;
    }

    public Chapter(String chapterName, String link, String releaseDate){
        this.chapterName = chapterName;
        this.link = link;
        this.releaseDate = releaseDate;
        this.haveRead = false;
    }

    @Override
    public String toString() {
        return chapterName;
    }


    public String getFormattedURL(){
        String[] correctLink = getLink().split("/");
        return correctLink[(correctLink.length - 2)];
    }
    public String getChapterNumber(){
        String[] names = this.chapterName.split("#");
        return names[names.length-1];
    }

    @Override
    public String getTitle() {
        return getChapterName();
    }

    //TODO: add a way to compare Chapters

    public void setHaveRead(Boolean haveRead) {
        this.haveRead = haveRead;
    }

    public Boolean getHaveRead() {
        return haveRead;
    }

    @NonNull
    public String getChapterName() {
        return this.chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
