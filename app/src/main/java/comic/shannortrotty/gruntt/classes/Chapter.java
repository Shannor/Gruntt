package comic.shannortrotty.gruntt.classes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shannortrotty on 2/13/17.
 * Class created to hold Chapter information.
 */

public class Chapter {
    @SerializedName("chapterName")
    private String chapterName;
    @SerializedName("link")
    private String link;
    @SerializedName("releaseDate")
    private String releaseDate;

    public Chapter(){

    }

    public Chapter(String chapterName, String link, String releaseDate){
        this.chapterName = chapterName;
        this.link = link;
        this.releaseDate =releaseDate;
    }

    @Override
    public String toString() {
        return chapterName;
    }
    public String getURLFormattedLink(){
        String[] correctLink = getLink().split("/");
        return correctLink[(correctLink.length - 2)];
    }
    public String getChapterNumber(){
        String[] names = this.chapterName.split("#");
        return names[names.length-1];
    }

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
