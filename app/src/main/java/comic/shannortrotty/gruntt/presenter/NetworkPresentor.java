package comic.shannortrotty.gruntt.presenter;

/**
 * Created by shannortrotty on 2/28/17.
 */

public interface NetworkPresentor {
    void onResume();
    void onItemClicked(int position);
    void onDestroy();
}
