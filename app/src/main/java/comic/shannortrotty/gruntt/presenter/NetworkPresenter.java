package comic.shannortrotty.gruntt.presenter;

import comic.shannortrotty.gruntt.classes.RequestType;

/**
 * Created by shannortrotty on 2/28/17.
 */

public interface NetworkPresenter {
    //Add param for which request to start ENUM possibly
    void startRequest(RequestType requestType);
    void onFinishedRequest();
    void onDestroy();
}
