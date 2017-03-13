package comic.shannortrotty.gruntt.classes;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by shannortrotty on 2/3/17.
 * Calls to hold the return call for Popular Comics Format.
 * Differs slightly from other Comic calls
 * Used in the retrofit calls
 */

public class PopularComic {

    @SerializedName("title")
    private String title;
    @SerializedName("link")
    private String link;
    @SerializedName("img")
    private String thumbnailUrl;
    @SerializedName("genre")
    private List<Genre> genres;

    public PopularComic(){
        this.title = null;
        this.link = null;
        this.thumbnailUrl = null;
        genres = new ArrayList<>();
    }

    public PopularComic(String title, String link, String thumbnailUrl, List<Genre> genres){
        this.title = title;
        this.link = link;
        this.thumbnailUrl = thumbnailUrl;
        this.genres = genres;
    }
    public PopularComic(String title, String link, String thumbnailUrl){
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

    public String getURLFormattedLink(){
        String[] correctLink = getLink().split("/");
        return correctLink[(correctLink.length - 1)];
    }
    public String getFormattedGenres(){
        List<Genre> genres = getGenres();
        String lastGenre = genres.get((genres.size() -1)).getGenre();
        String formatted = "";

        for(Genre genre : genres){
            //Last Genre
            if (genre.getGenre().equals(lastGenre)){
                formatted += genre.getGenre();
            }else{
                formatted += genre.getGenre() + ", ";

            }
        }
        return formatted;
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
        return "Comic{" +
                "title='" + title + '\'' +
                "link=" + link +
                '}';
    }
}
