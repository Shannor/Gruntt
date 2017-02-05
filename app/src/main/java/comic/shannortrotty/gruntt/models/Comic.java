package comic.shannortrotty.gruntt.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shannortrotty on 2/3/17.
 */

public class Comic{

    private String title;
    private String link;
    private String thumbnailUrl;
    private List<Genre> genres;

    public Comic(){
        this.title = null;
        this.link = null;
        this.thumbnailUrl = null;
        genres = new ArrayList<>();
    }

    public Comic(String title, String link, String thumbnailUrl, List<Genre> genres){
        this.title = title;
        this.link = link;
        this.thumbnailUrl = thumbnailUrl;
        this.genres = genres;
    }
    public Comic(String title, String link, String thumbnailUrl){
        this.title = title;
        this.link = link;
        this.thumbnailUrl = thumbnailUrl;
        this.genres = new ArrayList<>();
    }

    //Getters and Setters
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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public boolean addGenre(Genre genre){
        return this.genres.add(genre);
    }

    @Override
    public String toString() {
        return "Comic{" +
                "title='" + title + '\'' +
                '}';
    }
}
