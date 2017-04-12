package comic.shannortrotty.gruntt.utils;

import comic.shannortrotty.gruntt.classes.Comic;

/**
 * Created by shannortrotty on 2/14/17.
 * Interface for all Fragments dealing with Comic selections from a list
 */

public interface OnComicListener {
    void onListComicSelection(Comic comic, String originOfClick);
}
