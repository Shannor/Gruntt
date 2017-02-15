package comic.shannortrotty.gruntt.models;

/**
 * Created by shannortrotty on 2/13/17.
 * Class created to hold Chapter information.
 */

public class Chapter {
    private String chapterName;
    private String link;
    private String releaseDate;

    public Chapter(){
        this.chapterName = null;
        this.link = null;
        this.releaseDate = null;
    }

    public Chapter(String chapterName, String link, String releaseDate){
        this.chapterName = chapterName;
        this.link = link;
        this.releaseDate =releaseDate;
    }

    public String getChapterName() {
        return chapterName;
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
