package Managers;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import DataModels.Group;
import DataModels.LibraryFile;
import DataModels.Recipient;
import DataModels.User;
import UserUtils.Application;
import UserUtils.ImageLoader;
import Interfaces.PostFactory;

public class SessionManager {

    private static SessionManager sessionManager = null;

    SharedPreferences sharedPreferences = null;

    List<Recipient> recipients = new ArrayList();

    List<Group> userGroups ;

    List<LibraryFile> libraryFiles = new ArrayList<>();

    public List<LibraryFile> getLibraryFiles() {

        return libraryFiles;

    }

    public void setLibraryFiles(List<LibraryFile> libraryFiles) {

        this.libraryFiles = libraryFiles;

    }

    public List<PostFactory> posts;

    public void setPosts(List<PostFactory> posts) {

        this.posts = posts;

    }

    public List<PostFactory> getPosts() {

        return posts;

    }

    public List<Group> getUserGroups(){

        return userGroups;

    }

    ImageLoader imageLoader = null;

    public void saveUserGroups(List<Group> groups){

        userGroups = groups;

    }

    public void addRecipient(Recipient recipient){

        if(!checkExistingRecipient(recipient)) {

            recipients.add(recipient);

        }

    }

    private boolean checkExistingRecipient(Recipient recipient){

        for(int i = 0 ; i < recipients.size() ; i++){

            if( recipients.get(i).getId().equalsIgnoreCase(recipient.getId()) && recipients.get(i).getType() == recipient.getType()){

                return true;
            }
        }

        return false;
    }

    public List<Recipient> getRecipients() {

        return recipients;

    }

    public String getUsername(){

        return getInstance().getString("username");

    }

    public String getPassword(){

        return getInstance().getString("password");

    }

    public static SessionManager getInstance(){

        if(sessionManager == null){

            sessionManager = new SessionManager();
        }

        return sessionManager;
    }

    public String getToken(){

        return getInstance().getString("token");
    }

    public String getString(String key){

        return sharedPreferences.getString(key,"");

    }

    public boolean getBoolean(String key){

        return sharedPreferences.getBoolean(key,false);

    }

    public int getInteger(String key){

        return sharedPreferences.getInt(key,0);

    }

    public ImageLoader getImageLoader(){

        return imageLoader;

    }

    public SessionManager(){

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Application.getContext());

        imageLoader = new ImageLoader(Application.getContext());

    }

    public void clearData(){

        sharedPreferences.edit().clear().commit();

    }

    public User getCurrentUser(){

        User user = new Gson().fromJson(getString("user"),User.class);

        return user;
    }

    public void saveUser(User user){

        getInstance().saveString("user",new Gson().toJson(user));

    }

    public void saveString(String key,String value){

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(key,value);

        editor.commit();

    }
}
