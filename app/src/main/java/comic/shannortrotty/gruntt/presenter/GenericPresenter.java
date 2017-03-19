package comic.shannortrotty.gruntt.presenter;

import comic.shannortrotty.gruntt.classes.RequestType;
import retrofit2.Call;

/**
 * Created by shannortrotty on 2/28/17.
 */

public interface GenericPresenter {
    void startRequest(RequestType requestType);
    void onDestroy();
    void cancelRequest();
}
