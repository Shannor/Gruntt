package comic.shannortrotty.gruntt.presenter;

import android.content.Context;

import comic.shannortrotty.gruntt.services.APIError;
import comic.shannortrotty.gruntt.utils.Constants;
import comic.shannortrotty.gruntt.classes.Pages;
import comic.shannortrotty.gruntt.utils.RequestType;
import comic.shannortrotty.gruntt.model.NetworkModel;
import comic.shannortrotty.gruntt.view.GenericView;
import retrofit2.Call;

/**
 * Created by shannortrotty on 3/18/17.
 */

public class PagesPresenter implements ComicPresenter, NetworkModel.OnResponseItemListener<Pages> {

    private GenericView<Pages> genericView;
    private NetworkModel networkModel;
    private Context mContext;
    private boolean canceled;
    private Call call;
    private boolean finished;

    public PagesPresenter(Context context,GenericView<Pages> genericView, NetworkModel networkModel) {
        this.genericView = genericView;
        this.networkModel = networkModel;
        this.mContext = context;
        this.canceled = false;
        this.finished = false;
    }

    @Override
    public void startRequest(RequestType requestType) {
        genericView.showLoading();
        finished = false;
        if (requestType.getType() == RequestType.Type.LOAD) {
            networkModel.getChapterPages(
                    requestType.getExtras().get(Constants.COMIC_LINK),
                    requestType.getExtras().get(Constants.CHAPTER_NUMBER),
                    requestType.getExtras().get(Constants.SOURCE_TAG),
                    this
            );
        }else{

        }
    }

    @Override
    public void onDestroy() {
        genericView = null;
        networkModel = null;
        mContext = null;
    }

    @Override
    public void setRequestCall(Call<Pages> call) {
        this.call = call;
    }


    @Override
    public void cancelRequest() {
        if(!finished){
            this.canceled = true;
            this.call.cancel();
        }
    }

    @Override
    public void onItemSuccess(Pages item) {
        if(!canceled){
            genericView.setItem(item);
            genericView.hideLoading();
            finished =true;
        }
    }

    @Override
    public void onItemFailure(APIError error) {
        genericView.setErrorMessage(error);
        genericView.hideLoading();
    }

}
