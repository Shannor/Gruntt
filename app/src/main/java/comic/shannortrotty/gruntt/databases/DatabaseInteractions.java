package comic.shannortrotty.gruntt.databases;

import java.util.List;

import comic.shannortrotty.gruntt.classes.Chapter;
import comic.shannortrotty.gruntt.classes.ComicDetails;

/**
 * Created by shannortrotty on 4/12/17.
 */

public interface DatabaseInteractions {
    void addToFavroites(ComicDetails comicDetails);
    void removeFromFavorites(ComicDetails comicDetails);
    void saveComicList(String comicTitle, List<Chapter> chapterList, Chapter lastReadChapter);
    void updateComicProgression(String comicName, List<Chapter>chapterList,
                                Chapter last_read_chapter, int chapterIndex);
    boolean checkIfComicListSaved(String comicName);
    boolean isFavorite(String comicName);
}
