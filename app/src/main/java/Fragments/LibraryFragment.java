package Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Adapters.LibraryAdapter;
import DataModels.LibraryFile;
import Enums.ResponseCode;
import Interfaces.AbstractCallback;
import Interfaces.FunctionCaller;
import Interfaces.OnWebserviceFinishListener;
import Managers.FragmentManager;
import Managers.SessionManager;
import UserUtils.UIUtil;
import UserUtils.WebService;
import UserUtils.WebserviceRequestUtil;
import edubook.edubook.R;


public class LibraryFragment extends BaseFragment {

    List<LibraryFile> libraryFolders;

    ListView filesList;

    FunctionCaller caller;

    public void setCaller(FunctionCaller caller) {

        this.caller = caller;

    }

    AbstractCallback onFileSelectListener;

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            LibraryFile file = SessionManager.getInstance().getLibraryFiles().get(i);

            if(file.getFileList() != null && file.getType() == null){

                final List<LibraryFile> files = file.getFileList();

                showFolderContent(files);

            }
            else{

                if(onFileSelectListener != null){

                    onFileSelectListener.onResult(true,file);

                }

            }

        }
    };

    public OnWebserviceFinishListener getLibraryFilesListener = new OnWebserviceFinishListener() {

        @Override
        public void onFinish(WebService webService) {

            UIUtil.hideLoadingView();

            if(webService.getResponseCode() == ResponseCode.SUCCESS.getCode()) {

                parseLibraryFiles(webService);

                setFilesAdapter(null);

            }

            else{

                UIUtil.showErrorDialog();

            }

        }

    };

    public void setOnFileSelectListener(AbstractCallback onFileSelectListener) {

        this.onFileSelectListener = onFileSelectListener;

    }

    public LibraryFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public boolean onBackPressed() {

        FragmentManager.popCurrentVisibleFragment();

        return super.onBackPressed();
    }

    @Override
    public void onResume() {

        super.onResume();

        UIUtil.hideTabsView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        libraryFolders = SessionManager.getInstance().getLibraryFiles();

        if(libraryFolders.size() == 0){

            UIUtil.showLoadingView(rootView);

            caller.callFunction(getLibraryFilesListener);

        }
        else{

            setFilesAdapter(null);

        }

        initializeViewComponents();
    }

    private void setFilesAdapter(List<LibraryFile> files){

        filesList = (ListView) rootView.findViewById(R.id.files_list);

        filesList.setAdapter(new LibraryAdapter(files));

        rootView.findViewById(R.id.back_btn).setOnClickListener(null);

        filesList.setOnItemClickListener(itemClickListener);

    }

    private void showFolderContent(List<LibraryFile> files){

        filesList.setAdapter(new LibraryAdapter(files));

        rootView.findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                filesList.setAdapter(new LibraryAdapter(null));

                rootView.findViewById(R.id.back_btn).setOnClickListener(null);

            }
        });
    }

    public void parseLibraryFiles(WebService webService){

        Type libraryFileType = new TypeToken<List<LibraryFile>>() {}.getType();

        List<LibraryFile> libraryFilesMap = new Gson().fromJson(webService.getStrResponse().toString(), libraryFileType);

        SessionManager.getInstance().setLibraryFiles(libraryFilesMap);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return rootView = inflater.inflate(R.layout.fragment_library, null);

    }

    private void initializeViewComponents(){

        EditText searchInput = (EditText) rootView.findViewById(R.id.search_input);

        searchInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                searchText(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        rootView.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FragmentManager.popCurrentVisibleFragment();

            }
        });

    }

    private void searchText(CharSequence charSequence){

        List<LibraryFile> files = SessionManager.getInstance().getLibraryFiles();

        List<LibraryFile> resultFiles = new ArrayList<>();

        for(int j = 0 ; j < files.size() ; j ++){

            if(files.get(j).getName().contains(charSequence)){

                resultFiles.add(files.get(j));

            }

        }

        setFilesAdapter(resultFiles);
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
