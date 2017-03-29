package comic.shannortrotty.gruntt.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.SearchResultsActivity;


public class AdvancedSearchFragment extends Fragment{

    //True == Include, False == Exclude
    private Map<String,Boolean> queryParams;
    private Drawable includeIcon;
    private Drawable excludeIcon;
    private EditText editTextSearch;
    private Button submitSearchBtn;
    private Button resetSearchBtn;
    private RadioGroup radioGroup;

    public AdvancedSearchFragment() {
        // Required empty public constructor
    }


    public static AdvancedSearchFragment newInstance() {
        return new AdvancedSearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_advanced_search, container, false);
        queryParams = new HashMap<>();
        editTextSearch = ((EditText) view.findViewById(R.id.editText_advanced_search_fragment_keyword));
        includeIcon = ContextCompat.getDrawable(getContext(), R.drawable.ic_white_button_include);
        excludeIcon = ContextCompat.getDrawable(getContext(), R.drawable.ic_white_button_exclude);
        Button submitSearchBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_submit));
        Button resetSearchBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_reset));
        radioGroup = ((RadioGroup) view.findViewById(R.id.radioGroup_advanced_search));

        //All button different Genres.
        final Button marvelBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_marvel));
        final Button dcBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_dc_comics));
        final Button adventureBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_adventure));
        final Button dramaBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_drama));
        final Button horrorBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_horror));
        final Button militaryBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_military));
        final Button robotsBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_robots));
        final Button supernaturalBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_supernatural));
        final Button comedyBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_comedy));
        final Button fantasyBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_fantasy));
        final Button magicBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_magic));
        final Button mCLBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_movie_cinematic_link));
        final Button romanceBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_romance));
        final Button suspenseBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_suspense));
        final Button vertigoBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_vertigo));
        final Button crimeBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_crime));
        final Button goreBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_gore));
        final Button martialArtsBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_martial_arts));
        final Button mysteryBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_mystery));
        final Button scienceFictionBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_science_fiction));
        final Button tragedyBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_tragedy));
        final Button darkHorseBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_dark_horse));
        final Button cyborgsBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_cyborgs));
        final Button graphicsNovelsBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_graphic_novels));
        final Button matureBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_mature));
        final Button mythologyBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_mythology));
        final Button sportsBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_sports));
        final Button actionBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_action));
        final Button demonsBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_demons));
        final Button historicalBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_historical));
        final Button mechaBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_mecha));
        final Button psychologicalBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_psychological));
        final Button spyBtn = ((Button) view.findViewById(R.id.button_advanced_search_fragment_spy));
        //On Enter pressed function
        editTextSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    if(keyCode == KeyEvent.KEYCODE_ENTER) {
                        //Put Search function
                        startSearch();
                        return true;
                    }
                }
                return false;
            }
        });

        submitSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearch();
            }
        });

        resetSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields(marvelBtn, adventureBtn, dramaBtn, horrorBtn, militaryBtn, robotsBtn, supernaturalBtn,
                        dcBtn, comedyBtn, fantasyBtn, magicBtn, mCLBtn, romanceBtn, suspenseBtn, vertigoBtn,
                        crimeBtn, goreBtn, martialArtsBtn, mysteryBtn, scienceFictionBtn, tragedyBtn, darkHorseBtn,
                        cyborgsBtn, graphicsNovelsBtn, matureBtn, mythologyBtn, sportsBtn, actionBtn, demonsBtn,
                        historicalBtn, mechaBtn, psychologicalBtn, spyBtn);
            }
        });

        setListeners(marvelBtn, adventureBtn, dramaBtn, horrorBtn, militaryBtn, robotsBtn, supernaturalBtn,
                dcBtn, comedyBtn, fantasyBtn, magicBtn, mCLBtn, romanceBtn, suspenseBtn, vertigoBtn,
                crimeBtn, goreBtn, martialArtsBtn, mysteryBtn, scienceFictionBtn, tragedyBtn, darkHorseBtn,
                cyborgsBtn, graphicsNovelsBtn, matureBtn, mythologyBtn, sportsBtn, actionBtn, demonsBtn,
                historicalBtn, mechaBtn, psychologicalBtn, spyBtn);
        return view;
    }

    private void handleAction(String buttonKey, Button button){
        //First click not included yet
        if(!queryParams.containsKey(buttonKey)){
            queryParams.put(buttonKey, true);
            button.setCompoundDrawablesWithIntrinsicBounds(includeIcon, null, null, null);
            button.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.button_include_background));
        }else if(queryParams.get(buttonKey)){
            //Is set to include change to Exclude
            queryParams.put(buttonKey, false);
            button.setCompoundDrawablesWithIntrinsicBounds(excludeIcon, null, null, null);
            button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_exclude_background));
        }else {
            //Last case, remove from list and don't want to search on this category
            queryParams.remove(buttonKey);
            button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_neutral_background));
            button.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }

    private void clearFields(Button... buttons){
        queryParams.clear();
        editTextSearch.setText("");
        radioGroup.check(R.id.radioButton_advanced_search_fragment_all);
        for(Button button : buttons){
            button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_neutral_background));
            button.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }

    private void startSearch(){
        String keyword = editTextSearch.getText().toString();
        if(!keyword.equals("")){
            RadioButton radioButton = (RadioButton)radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
            String status = radioButton.getText().toString();
            String include = null;
            String exclude = null;
            for(Map.Entry<String, Boolean> entry : queryParams.entrySet()){
                if(entry.getValue()){
                    //In include
                    if(include == null){
                        include = entry.getKey();
                    }else{
                        include += "," + entry.getKey();
                    }
                }else{
                    //Exclude
                    if(exclude == null){
                        exclude = entry.getKey();
                    }else{
                        exclude += "," + entry.getKey();
                    }
                }
            }
            SearchResultsActivity.startActivity(getContext(), keyword, include, exclude, status);
        }else{
            //Return notification
            Toast.makeText(getContext(), "Must provide keyword!", Toast.LENGTH_SHORT).show();
        }

    }

    private void setListeners(final Button... buttons){
        for(int i = 0; i < buttons.length; i++){
            final String name = buttons[i].getText().toString();
            final int index = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleAction(name,buttons[index]);
                }
            });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

}
