package Fragments;
public class NewGroupNoteFragment extends NewNoteFragment {

    @Override
    public void onResume() {

        super.onResume();

        processGroupStaff();

    }
}
