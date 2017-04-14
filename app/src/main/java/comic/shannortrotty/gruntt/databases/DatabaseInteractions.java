package comic.shannortrotty.gruntt.databases;

import java.util.List;

import comic.shannortrotty.gruntt.classes.Chapter;
import comic.shannortrotty.gruntt.classes.ComicDetails;

/**
 * Created by shannortrotty on 4/12/17.
 */

public interface DatabaseInteractions {
    void saveComicDetails(ComicDetails comicDetails);
    void deleteComicDetails(ComicDetails comicDetails);
    void saveComicList(String comicTitle, List<Chapter> chapterList, Chapter lastReadChapter);
    void deleteComicList(String comicName);
    void updateComicProgression(String comicName, List<Chapter>chapterList,
                                Chapter last_read_chapter, int chapterIndex);
    List<Chapter> getSavedComicList(String comicName);
    ComicDetails getComicDetails(String comicName);
}
