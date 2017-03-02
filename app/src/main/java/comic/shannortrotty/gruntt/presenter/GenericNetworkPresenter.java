package comic.shannortrotty.gruntt.presenter;

import comic.shannortrotty.gruntt.classes.RequestType;

/**
 * Created by shannortrotty on 2/28/17.
 */

public interface GenericNetworkPresenter {
    void startRequest(RequestType requestType);
    void onDestroy();
}
