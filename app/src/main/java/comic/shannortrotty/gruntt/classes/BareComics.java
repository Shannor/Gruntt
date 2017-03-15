package comic.shannortrotty.gruntt.classes;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shannortrotty on 3/13/17.
 * Comic class for all Comics
 * Base because has significantly less than other comic options
 */

public class BareComics implements Comic{
    @SerializedName("title")
    private String title;
    @SerializedName("link")
    private String link;

    public BareComics() {

    }

    public BareComics(String title, String link) {
        this.title = title;
        this.link = link;
    }

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

    @Override
    public String getFormattedURL() {
        String[] correctLink = getLink().split("/");
        return correctLink[(correctLink.length - 1)];
    }

    @Override
    public String toString() {
        return "BareComics{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
