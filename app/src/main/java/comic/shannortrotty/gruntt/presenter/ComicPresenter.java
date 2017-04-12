package comic.shannortrotty.gruntt.presenter;

import comic.shannortrotty.gruntt.utils.RequestType;

/**
 * Created by shannortrotty on 2/28/17.
 */

public interface ComicPresenter {
    void startRequest(RequestType requestType);
    void onDestroy();
    void cancelRequest();
}
