package comic.shannortrotty.gruntt.classes;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shannortrotty on 2/16/17.
 * Class to hold the details about a particular comic.
 */

public class ComicDetails {

    @SerializedName("description")
    private String description;
    @SerializedName("largeImg")
    private String largeImgURL;
    @SerializedName("name")
    private String title;
    @SerializedName("alternate Name")
    private String altTitle;
    @SerializedName("status")
    private String status;
    @SerializedName("author")
    private String author;
    @SerializedName("genre")
    private String genre;
    @SerializedName("year of Release")
    private String releaseDate;

    private Bitmap localBitmap;


    public ComicDetails(){
        this.description = " -";
        this.title = " -";
        this.altTitle = " -";
        this.status = " -";
        this.author = " -";
        this.genre = " -";
        this.releaseDate = " -";

    }
    public String getFormattedName(){
        return "Name: " +  this.title;
    }

    public String getFormattedAltName(){
        return "Alternate Name: "  + this.altTitle;
    }

    public String getFormattedStatus(){
        return "Status: " + this.status;
    }

    public String getFormattedGenre(){
        return "Genre: " + this.genre;
    }

    public String getFormattedReleaseDate(){
        return "Release Date: " + this.releaseDate;
    }

    public String getFormattedAuthor(){
        return "Author(s): " + this.author;
    }

    public String getFormattedDescription(){
        return "Description: " + this.description;
    }

//    ***********************GETTERS AND SETTERS**********************************


    public Bitmap getLocalBitmap() {
        return localBitmap;
    }

    public void setLocalBitmap(Bitmap localBitmap) {
        this.localBitmap = localBitmap;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLargeImgURL() {
        return largeImgURL;
    }

    public void setLargeImgURL(String largeImgURL) {
        this.largeImgURL = largeImgURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAltTitle() {
        return altTitle;
    }

    public void setAltTitle(String altTitle) {
        this.altTitle = altTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    @Override
    public String toString() {
        return "ComicDetails{" +
                "description='" + description + '\'' +
                ", largeImgURL='" + largeImgURL + '\'' +
                ", title='" + title + '\'' +
                ", altTitle='" + altTitle + '\'' +
                ", status='" + status + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }
}
