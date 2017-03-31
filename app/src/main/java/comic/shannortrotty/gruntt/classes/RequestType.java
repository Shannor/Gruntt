package comic.shannortrotty.gruntt.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shannortrotty on 2/28/17.
 * Used to Specify the Request type of a request and pass the required information for it.
 */

public class RequestType {

    /**
     *
     */
    public enum Type{
//        New Types may keep only these
//       TODO: maybe better types
        UPDATE, LOAD, SAVE,
//        If Network check network first. Database try database first, Either check locally first
        NETWORK, DATABASE, EITHER
    }

//    TODO:Add ENUM Attributes
    private Type type;
    private Map<String,String> extras;


    public RequestType(){
        extras = new HashMap<>();
    }

    public RequestType(Type type) {
        this.type = type;
        extras = new HashMap<>();
    }

    public RequestType(Type type, Map<String, String> extras) {
        this.type = type;
        this.extras = extras;
    }

    public Map<String, String> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, String> extras) {
        this.extras = extras;
    }

    public void addExtras(String key, String value){
        this.extras.put(key, value);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}

