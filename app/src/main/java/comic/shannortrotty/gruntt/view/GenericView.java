package comic.shannortrotty.gruntt.view;

import java.util.List;

import comic.shannortrotty.gruntt.services.APIError;

/**
 * Created by shannortrotty on 2/28/17.
 */

public interface GenericView<T> {

    void showLoading();
    void hideLoading();
    void setItems(List<T> items);
    void setItem(T item);
    void setErrorMessage(APIError errorMessage);
}
