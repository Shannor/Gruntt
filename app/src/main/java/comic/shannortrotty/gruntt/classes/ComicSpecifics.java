package comic.shannortrotty.gruntt.classes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shannortrotty on 2/16/17.
 * Class to hold the details about a particular comic.
 */

public class ComicSpecifics {

    @SerializedName("description")
    private String description;
    @SerializedName("largeImg")
    private String largeImgURL;
    @SerializedName("name")
    private String name;
    @SerializedName("alternate name")
    private String altName;
    @SerializedName("status")
    private String status;
    @SerializedName("author")
    private String author;
    @SerializedName("genre")
    private String genre;
    @SerializedName("year of Release")
    private String releaseDate;


    public ComicSpecifics(){
        this.description = " -";
        this.name = " -";
        this.altName = " -";
        this.status = " -";
        this.author = " -";
        this.genre = " -";
        this.releaseDate = " -";

    }
    public String getFormattedName(){
        return "Name: " +  this.name;
    }

    public String getFormattedAltName(){
        return "Alternate Name: "  + this.altName;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAltName() {
        return altName;
    }

    public void setAltName(String altName) {
        this.altName = altName;
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
        return "ComicSpecifics{" +
                "description='" + description + '\'' +
                ", largeImgURL='" + largeImgURL + '\'' +
                ", name='" + name + '\'' +
                ", altName='" + altName + '\'' +
                ", status='" + status + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }
}
