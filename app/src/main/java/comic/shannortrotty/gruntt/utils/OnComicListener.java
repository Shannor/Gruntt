package comic.shannortrotty.gruntt.utils;

import comic.shannortrotty.gruntt.classes.ComicInterface;

/**
 * Created by shannortrotty on 2/14/17.
 * Interface for all Fragments dealing with ComicInterface selections from a list
 */

public interface OnComicListener {
    void onListComicSelection(ComicInterface comicInterface, String originOfClick);
}
