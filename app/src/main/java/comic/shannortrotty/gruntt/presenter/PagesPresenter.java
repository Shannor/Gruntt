package comic.shannortrotty.gruntt.presenter;

import android.content.Context;

import java.util.List;

import comic.shannortrotty.gruntt.classes.BareComicsCategory;
import comic.shannortrotty.gruntt.classes.Constants;
import comic.shannortrotty.gruntt.classes.RequestType;
import comic.shannortrotty.gruntt.model.NetworkModel;
import comic.shannortrotty.gruntt.view.GenericView;
import retrofit2.Call;

/**
 * Created by shannortrotty on 3/18/17.
 */

public class PagesPresenter implements GenericPresenter, NetworkModel.OnResponseListListener<String> {

    private GenericView<String> genericView;
    private NetworkModel networkModel;
    private Context mContext;
    private boolean canceled;
    private Call call;
    private boolean finished;

    public PagesPresenter(Context context,GenericView<String> genericView, NetworkModel networkModel) {
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
        if (requestType.getType() == RequestType.Type.PAGES) {
            networkModel.getComicPages(
                    requestType.getExtras().get(Constants.COMIC_LINK),
                    requestType.getExtras().get(Constants.CHAPTER_NUMBER),
                    this
            );
        }else{

        }
    }

    @Override
    public void onDestroy() {
        genericView = null;
        networkModel = null;
    }

    @Override
    public void onCanceledRequest(Call<List<String>> call) {
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
    public void onListFinished(List<String> items) {
        if(!canceled){
            genericView.setItems(items);
            genericView.hideLoading();
            finished =true;
        }

    }
}
