package comic.shannortrotty.gruntt.services;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;

/**
 * Created by shannortrotty on 2/14/17.
 * Class that will handle calls to the Requested Service
 * Abstracts out the Service being use so they can be switched during run time
 */

public class ServiceMediator {
    //String that will hold the Service to call
    private  String serviceTag = null;
    private static ServiceMediator mInstance = null;
    private static final String TAG = "ServiceMediator";

    private ServiceMediator(){
        //Default constructor
    }
    public static synchronized ServiceMediator getInstance(){
        if(mInstance == null){
            mInstance = new ServiceMediator();
        }
        return  mInstance;
    }

    public void setServiceTag(String serviceTag){
        this.serviceTag = serviceTag;
    }
    public void getPopularList(Context context, String pageNumber){
        if(serviceTag.equals(ComicTvHttpService.TAG)){
            ComicTvHttpService.startActionPoplarList(context, pageNumber);
        }else{
            Log.e(TAG, "No Service matches the current Service Tag. Set the Mediator's Service Tag var.");
        }
    }

    public void getIssueList(Context context, String comicName){
        if(serviceTag.equals(ComicTvHttpService.TAG)){
            ComicTvHttpService.startActionGetChapterList(context, comicName);
        }else{
            Log.e(TAG, "No Service matches the current Service Tag. Set the Mediator's Service Tag var.");
        }
    }
}