package comic.shannortrotty.gruntt.presenter;

import java.util.List;

import comic.shannortrotty.gruntt.services.APIError;
import comic.shannortrotty.gruntt.utils.Constants;
import comic.shannortrotty.gruntt.utils.RequestType;
import comic.shannortrotty.gruntt.classes.SearchComic;
import comic.shannortrotty.gruntt.model.NetworkModel;
import comic.shannortrotty.gruntt.view.GenericView;
import retrofit2.Call;

/**
 * Created by shannortrotty on 3/24/17.
 */

public class SearchComicPresenter implements ComicPresenter, NetworkModel.OnResponseListListener<SearchComic> {

    private Call<List<SearchComic>> call;
    private GenericView<SearchComic> genericView;
    private NetworkModel networkModel;
    private boolean isFinished;

    public SearchComicPresenter(GenericView<SearchComic> genericView, NetworkModel networkModel) {
        this.genericView = genericView;
        this.networkModel = networkModel;
    }

    @Override
    public void onDestroy() {
        cancelRequest();
        genericView = null;
        networkModel = null;
        call = null;
    }

    @Override
    public void cancelRequest() {
        if(!isFinished){
            call.cancel();
        }
    }

    @Override
    public void startRequest(RequestType requestType) {
       isFinished = false;
        genericView.showLoading();
        if(requestType.getType() == RequestType.Type.LOAD){
            networkModel.searchComics(
                    requestType.getExtras().get(Constants.SEARCH_COMIC_KEYWORD),
                    requestType.getExtras().get(Constants.SEARCH_COMIC_INCLUDE),
                    requestType.getExtras().get(Constants.SEARCH_COMIC_EXCLUDE),
                    requestType.getExtras().get(Constants.SEARCH_COMIC_STATUS),
                    requestType.getExtras().get(Constants.PAGE_NUMBER),
                    this
            );
        }
    }

    @Override
    public void onListSuccess(List<SearchComic> items) {
        isFinished = true;
        genericView.setItems(items);
        genericView.hideLoading();
    }

    @Override
    public void onAPIFailure(Throwable throwable) {

    }

    @Override
    public void onListFailure(APIError error) {
        genericView.setErrorMessage(error);
        genericView.hideLoading();
    }

    @Override
    public void setRequestListCall(Call<List<SearchComic>> call) {
        this.call = call;
    }
}
