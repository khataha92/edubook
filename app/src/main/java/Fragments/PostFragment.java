package Fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Adapters.RecipientSpinnerAdapter;
import DataModels.Recipient;
import Enums.RecieverType;
import Interfaces.AbstractCallback;
import Managers.FragmentManager;
import Managers.SessionManager;
import UserUtils.Application;
import UserUtils.Constants;
import UserUtils.UIUtil;
import de.hdodenhof.circleimageview.CircleImageView;
import edubook.edubook.R;

public class PostFragment extends BaseFragment {

    public Spinner recipient;

    public RecieverType type;

    public List<Recipient> targetRecipients = new ArrayList<>();

    public List<Recipient> selectableRecipients;

    Button post;

    AutoCompleteTextView recipientList;

    LinearLayout tagsContainer;

    public AdapterView.OnItemClickListener autocompleteClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            final Recipient recipient = ((ArrayAdapter<Recipient>)adapterView.getAdapter()).getItem(i);

            UIUtil.addTagToLayout(tagsContainer, recipient, new AbstractCallback() {

                @Override
                public void onResult(boolean isSuccess, Object result) {

                    targetRecipients.remove(result);

                    selectableRecipients.add(recipient);

                    addRecipientsAdapter();

                }
            });

            targetRecipients.add(recipient);

            selectableRecipients.remove(recipient);

            addRecipientsAdapter();

            recipientList.setText("");

        }
    };


    public void loadImage(){

        CircleImageView imageView = (CircleImageView)rootView.findViewById(R.id.profile_image);

        Drawable drawable = Application.getContext().getResources().getDrawable(R.drawable.male_profile_image);

        String imageUrl = SessionManager.getInstance().getCurrentUser().getThumb();

        Picasso.with(imageView.getContext()).load(imageUrl).error(drawable).into(imageView);

    }
    public void addRecipientsAdapter(){

        ArrayAdapter<Recipient> adapter;

        adapter = new ArrayAdapter<Recipient>(getContext(),

                android.R.layout.simple_list_item_1, selectableRecipients);

        recipientList.setAdapter(adapter);

    }

    AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            RecieverType selectedType = ((RecipientSpinnerAdapter) recipient.getAdapter()).getSelectedType(i);

            type = selectedType;

            targetRecipients.clear();

            List<Recipient> array = SessionManager.getInstance().getRecipients();

            selectableRecipients = new ArrayList<>();

            recipientList.setEnabled(true);

            tagsContainer.removeAllViews();

            switch (selectedType){

                case ONLY_ME:

                    recipientList.setEnabled(false);

                    String id = SessionManager.getInstance().getCurrentUser().getId();

                    String name = SessionManager.getInstance().getCurrentUser().getName();

                    Recipient recipient = new Recipient(Recipient.RecipientType.ONLY_ME,id,name);

                    targetRecipients.add(recipient);

                    break;


                case GROUPS:

                    for(int j = 0 ; j < array.size() ; j ++){

                        if(array.get(j).getType() == Recipient.RecipientType.GROUP){

                            selectableRecipients.add(array.get(j));
                        }

                    }

                    break;

                case STUDENTS:

                    for(int j = 0 ; j < array.size() ; j ++){

                        if(array.get(j).getType() == Recipient.RecipientType.STUDENT){

                            selectableRecipients.add(array.get(j));
                        }

                    }

                    break;

                case USERS:

                    for(int j = 0 ; j < array.size() ; j ++){

                        if(array.get(j).getType() == Recipient.RecipientType.CONTACT){

                            selectableRecipients.add(array.get(j));
                        }

                    }

                    break;


            }

            addRecipientsAdapter();

            recipientList.setOnItemClickListener(autocompleteClickListener);

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }

    };

    public PostFragment() {


    }

    public void prepareFragment(){

        UIUtil.hideTabsView();

        recipient = (Spinner)rootView.findViewById(R.id.recipient);

        recipientList = (AutoCompleteTextView) rootView.findViewById(R.id.recipient_input);

        List<RecieverType> recipientList = Constants.getRecieverTypes();

        recipient.setAdapter(new RecipientSpinnerAdapter(recipientList));

        recipient.setOnItemSelectedListener(spinnerListener);

        tagsContainer = (LinearLayout) rootView.findViewById(R.id.tags_container);

        post = (Button) rootView.findViewById(R.id.post);

        post.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                addPost();

            }
        });

        rootView.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FragmentManager.popCurrentVisibleFragment();

            }
        });

    }

    public void addPost(){


    }

    @Override
    public void onResume() {

        super.onResume();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        prepareFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return null;
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
