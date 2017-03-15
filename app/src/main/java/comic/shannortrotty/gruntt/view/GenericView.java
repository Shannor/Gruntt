package comic.shannortrotty.gruntt.view;

import java.util.List;

/**
 * Created by shannortrotty on 2/28/17.
 */

public interface GenericView<T> {

    void showLoading();
    void hideLoading();
    void setItems(List<T> items);
    void setItem(T item);
}
