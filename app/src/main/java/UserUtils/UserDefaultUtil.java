package UserUtils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;


import com.google.gson.internal.LinkedTreeMap;
import com.ruily.crop.FileUtils;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DataModels.Group;
import DataModels.Post;
import Enums.ErrorType;
import Enums.HolderType;
import Enums.RecieverType;
import Managers.SessionManager;
import edubook.edubook.R;

/**
 * Created by mohammad on 4/19/15.
 * This is the user default util that will handle saving and getting user values
 */




public class UserDefaultUtil {

    private static final String TAG = UserDefaultUtil.class.getName();

    private static SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Application.getContext());

    static Map<String,View.OnClickListener> listeners = new HashMap<>();

    public static Map<String, View.OnClickListener> getListeners() {

        return listeners;

    }

    // Saves string value to shared preference

    public static void startGalleryIntent() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        intent.setType("image/*");

        Application.getCurrentActivity().startActivityForResult(intent, Constants.REQUEST_GALLERY);

    }

    public static View.OnClickListener getChangeImageListener(){

        return listeners.get(SessionManager.getInstance().getCurrentUser().getId());

    }

    public static String dispatchTakePictureIntent() {

        String mCurrentPhotoPath = "";

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(Application.getContext().getPackageManager()) != null) {

            File photoFile;

            photoFile = FileUtils.getOutputMediaFileUri();

            mCurrentPhotoPath = "file:" + photoFile.getAbsolutePath();

            if (photoFile != null) {

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));

                Application.getCurrentActivity().startActivityForResult(takePictureIntent, Constants.REQUEST_CAMERA);

            }
        }

        return mCurrentPhotoPath;
    }

    public static int getRecieverIcon(RecieverType type){

        switch (type){

            case USERS:

                return R.drawable.connections;

            case ONLY_ME:

                return R.drawable.only_me;

            case GROUPS:

                return R.drawable.groups;

            case STUDENTS:

                return R.drawable.students;

            default:

                return R.drawable.connections;
        }
    }

    public static HolderType getPostType(Post post){

        if(post.getAssignment() != null){

            return HolderType.ASSIGNMENT;
        }

        if(post.getNote() != null){

            return HolderType.NOTE;
        }

        if(post.getEvent() != null){

            return HolderType.EVENT;
        }

        return HolderType.POLL;

    }

    public static String getRecieverLabel(RecieverType type){

        int stringResourceId = -1;

        switch (type){

            case USERS:

                stringResourceId = R.string.users;

                break;

            case ONLY_ME:

                stringResourceId =  R.string.only_me;

                break;

            case GROUPS:

                stringResourceId = R.string.groups;

                break;

            case STUDENTS:

                stringResourceId = R.string.students;

                break;

            default:

                stringResourceId =  R.string.nill;

                break;

        }

        return Application.getContext().getString(stringResourceId);
    }

    public static String getStringWithMaxLength(String string, int length){

        if(string.length() < length){

            return string;

        }

        return string.substring(0,length) + " ...";

    }

    public static List<Group> convertLinkedTreeMap(List<LinkedTreeMap> linkedTreeMaps){

        List<Group> groups = new ArrayList<>();

        for(int i = 0 ;i < linkedTreeMaps.size() ; i++){

            Group group = new Group();

            Object desc = linkedTreeMaps.get(i).get("description");

            group.setDescription((String)desc);

            group.setId(""+ linkedTreeMaps.get(i).get("id"));

            group.setName(""+linkedTreeMaps.get(i).get("name"));

            group.setUserId(""+linkedTreeMaps.get(i).get("user_id"));

            groups.add(group);

        }

        return groups;
    }

    public static ErrorType validatePassword(String currentPassword, String newPassword, String confirmation){

        ErrorType errorType = ErrorType.VALID;

        String validCurrentPassword = SessionManager.getInstance().getPassword();

        if(!md5(validCurrentPassword).equalsIgnoreCase(md5(currentPassword))){

            errorType = ErrorType.WRONG_CURRENT_PASSWORD;

            return errorType;

        }

        if(md5(currentPassword).equalsIgnoreCase(md5(newPassword))){

            errorType = ErrorType.OLD_PASS_EQUALS_NEW;

        }
        else{

            if(!md5(newPassword).equalsIgnoreCase(md5(confirmation))){

                errorType = ErrorType.NEW_PASS_NOT_EQUAL_CONFIRMATION;

            }
            else if(newPassword.length() < 6){

                errorType = ErrorType.NEW_PASSWORD_LENGHT;

            }

        }


        return errorType;

    }

    public static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    private static void setStringValue(final String value, final String key) {

        if ((key == null) || preferences == null) {

            return;

        }

        preferences.edit().putString(key, value).apply();

    }

    public static String getUserLanguage() {

        String deviceLanguage = SessionManager.getInstance().getString(UserDefaultKeys.DEVICE_LANGUAGE.toString());

        if (deviceLanguage != null) {

            return deviceLanguage;
        }

        return getDeviceLanguage();

    }


    public static void setPopularCitiesLastUpdateLanguage(final String lastUpdateLanguage) {

        setStringValue(lastUpdateLanguage, UserDefaultKeys.POPULAR_CITIES_LAST_UPDATE_LANGUAGE.toString());

    }

    public static void setCurrencyLastUpdateLanguage(final String updateLanguage) {

        setStringValue(updateLanguage, UserDefaultKeys.CURRENCY_LAST_UPDATE_LANGUAGE.toString());

    }

    public static String getDeviceLanguage() {

        Resources res = Application.getContext().getResources();
        // Change locale settings in the app.

        android.content.res.Configuration conf = res.getConfiguration();

        String lang = conf.locale.getLanguage();

        if ("ar".equalsIgnoreCase(lang) || "en".equalsIgnoreCase(lang)) {

            return lang;

        } else {

            return "en";
        }
    }

    /**
     * Get current language whatever it is
     * @return String
     */
    public static String getDeviceLanguageForAll() {

        Resources res = Application.getContext().getResources();
        // Change locale settings in the app.

        android.content.res.Configuration conf = res.getConfiguration();

        return conf.locale.getLanguage();

    }

    public static boolean deviceLanguageIsArabic() {

        return "ar".equalsIgnoreCase(getUserLanguage());

    }

    public static boolean deviceLanguageIsEnglish() {

        return "en".equalsIgnoreCase(getUserLanguage());

    }

    public static boolean deviceLanguageIsFrench() {

        return "fr".equalsIgnoreCase(getUserLanguage());

    }


    public static boolean deviceLanguageIsRTL() {

        return "ar".equalsIgnoreCase(getUserLanguage());

    }

    enum UserDefaultKeys {

        DEVICE_LANGUAGE ("DEVICE_LANGUAGE"), UUID ("UUID"), POPULAR_CITIES_LAST_UPDATE_DATE("POPULAR_CITIES_LAST_UPDATE_DATE"), CURRENCY_LAST_UPDATE_DATE("CURRENCY_LAST_UPDATE_DATE"), POPULAR_CITIES_LAST_UPDATE_LANGUAGE("POPULAR_CITIES_LAST_UPDATE_LANGUAGE"), CURRENCY_LAST_UPDATE_LANGUAGE("CURRENCY_LAST_UPDATE_LANGUAGE"), COUNTRIES_CITIES_LAST_UPDATE_LANGUAGE ("CURRENCY_LAST_UPDATE_LANGUAGE"), GUEST_INFO("GUEST_INFO"), COUNTRIES_AND_CITIES_LAST_UPDATE_DATE("COUNTRIES_AND_CITIES_LAST_UPDATE_DATE"),
        COUNTRIES_AND_CITIES("COUNTRIES_AND_CITIES"), POPULAR_CITIES("POPULAR_CITIES"), VERIFIED_PHONE_NUMBER("VERIFIED_PHONE_NUMBER"), VERIFIED_PHONE_NUMBERS("VERIFIED_PHONE_NUMBERS"), DEFAULT_COUNTRY_CODE("DEFAULT_COUNTRY_CODE"), CURRENCIES("CURRENCIES"), HOLIDAYS("HOLIDAYS"), USER_CURRENCY("USER_CURRENCY"), MY_ORDERS("MY_ORDERS"), OFFLINE_NUMBERS("OFFLINE_NUMBERS"), RECENT_SEARCH("RECENT_SEARCH"), DEVICE_GEO_INFO("DEVICE_GEO_INFO"), PUSH_NOTIFICATION_SUBSCRIBED ("PUSH_NOTIFICATION_SUBSCRIBED")
        , TREND_SEARCH("TREND_SEARCH");

        private String value;

        UserDefaultKeys(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {

            return this.getValue();
        }

    }

}
