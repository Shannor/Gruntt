package comic.shannortrotty.gruntt.classes;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shannortrotty on 2/13/17.
 * Class created to hold Chapter information.
 */

public class Chapter implements ComicInterface {

    private static String TAG = "Chapter";

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
    public int getChapterNumber(){
        try {
            String[] names = this.chapterName.split("#");
            String value = names[names.length-1];
            if( value.equals("Full")){
                return 1;
            }
            return Integer.parseInt(value);
        }catch (NumberFormatException e){
            Log.e(TAG, "getChapterNumber: value provided is not a string.", e);
            return 0;
        }
    }

    @Override
    public String getTitle() {
        return getChapterName();
    }

    //TODO: add a way to compare Chapters

    public Boolean getHaveRead() {
        return haveRead;
    }

    public void setHaveRead(Boolean haveRead) {
        this.haveRead = haveRead;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chapter chapter = (Chapter) o;

        if (!chapterName.equals(chapter.chapterName)) return false;
        return link.equals(chapter.link);

    }

    @Override
    public int hashCode() {
        int result = chapterName.hashCode();
        result = 31 * result + link.hashCode();
        return result;
    }
}
