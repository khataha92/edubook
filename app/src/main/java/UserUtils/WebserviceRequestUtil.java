package UserUtils;

import android.util.Log;
import org.json.JSONObject;

import DataModels.Group;
import DataModels.RecieversModel;
import Enums.ErrorType;
import Enums.GroupUserStatus;
import Enums.RecieverType;
import Enums.RequestServices;
import Interfaces.OnWebserviceFinishListener;
import Managers.SessionManager;

public class WebserviceRequestUtil {

    private static String TAG = WebserviceRequestUtil.class.getSimpleName();

    public static void login(final String name, final String password,OnWebserviceFinishListener listener){

        final WebService ws = getLoginWebService(name,password,listener);

        ws.start();

    }

    public static void changeGroupDescription(Group group, String description,OnWebserviceFinishListener listener){

        WebService webService = postWebService(listener);

        webService.addParams("_method","put");

        webService.addParams("name",group.getName());

        webService.setService(String.format(RequestServices.EDIT_GROUP_DESCRIPTION.getValue(),group.getId()));

        webService.addParams("description",description);

        webService.addParams("institute_id",group.getInstitute());

        webService.start();

    }

    public static void getGroupMembers(String groupId,OnWebserviceFinishListener listener){

        WebService webService = getWebService(listener);

        webService.setService(String.format(RequestServices.GET_GROUP_MEMBERS.getValue(),groupId));

        webService.start();

    }

    public static void changeUserProfile(String imagePath, OnWebserviceFinishListener listener){

        String service = RequestServices.CHANGE_USER_PROFILE.getValue();

        ImageUploader uploader = new ImageUploader(Constants.server,Constants.port,service);

        uploader.setListener(listener);

        uploader.setImagePath(imagePath);

        uploader.upload();

    }

    public static void setNotificationOpened(String notificationId, OnWebserviceFinishListener listener){

        WebService webService = getWebService(listener);

        String service = RequestServices.SET_NOTIFICATION_OPENED.getValue();

        webService.setService(String.format(service,notificationId));

        webService.start();

    }

    public static void getUserNotification(OnWebserviceFinishListener listener){

        WebService webService = getWebService(listener);

        webService.setService(RequestServices.GET_USER_NOTIFICATIONS.getValue());

        webService.start();

    }

    public static void getNotificationCount(OnWebserviceFinishListener listener){

        WebService webService = getWebService(listener);

        webService.setService(RequestServices.GET_NOTIFICATION_COUNT.getValue());

        webService.start();

    }

    public static void getParentAccessKey(String userId,OnWebserviceFinishListener listener){

        WebService webService = getWebService(listener);

        webService.setService(String.format(RequestServices.GET_PARENT_ACCESS_KEY.getValue(),userId));

        webService.start();
    }

    public static void removeUserFromGroup(String userId, String groupId, OnWebserviceFinishListener listener){

        WebService webService = deleteWebService(listener);

        webService.setService(String.format(RequestServices.REMOVE_USER_FROM_GROUP.getValue(),groupId,userId));

        webService.start();

    }

    public static void removeProfileImage(String userId,OnWebserviceFinishListener listener){

        WebService webService = deleteWebService(listener);

        webService.setService(String.format(RequestServices.REMOVE_PROFILE_IMAGE.getValue(),userId));

        webService.start();

    }

    public static void changeUserPassword(String oldPass,String newPass, OnWebserviceFinishListener listener){

        WebService webService = postWebService(listener);

        webService.setService(RequestServices.CHANGE_USER_PASSWORD.getValue());

        webService.addParams("old_password",oldPass);

        webService.addParams("password",newPass);

        webService.addParams("password_confirmation",newPass);

        webService.start();

    }

    public static void addEvent(String title,String description,String startDate,String endDate, RecieversModel model, OnWebserviceFinishListener listener){

        WebService webService = postWebService(listener);

        webService.setService(RequestServices.ADD_POST.getValue());

        webService.addParams("event[title]",title);

        webService.addParams("event[description]",description);

        webService.addParams("event[start_date]",startDate);

        webService.addParams("event[end_date]",endDate);

        addRecievers(webService,model);

        webService.start();

    }

    public static void changeUserEmail(String email, OnWebserviceFinishListener listener){

        WebService webService = postWebService(listener);

        String userId = SessionManager.getInstance().getCurrentUser().getId();

        webService.setService(String.format(RequestServices.CHANGE_USER_EMAIL.getValue(),userId));

        webService.addParams("email",email);

        webService.start();

    }

    public static void getPostLikes(String postId,OnWebserviceFinishListener listener){

        WebService webService = getWebService(listener);

        webService.setService(String.format(RequestServices.GET_POST_LIKES.getValue(),postId));

        webService.start();
    }

    public static void getGroupStream(String groupId, OnWebserviceFinishListener listener){

        WebService webService = getWebService(listener);

        webService.setService(String.format(RequestServices.GET_GROUP_STREAM.getValue(),groupId));

        webService.start();

    }

    public static void deletePost(String postId, OnWebserviceFinishListener listener){

        WebService webService = deleteWebService(listener);

        webService.setService(String.format(RequestServices.DELETE_POST.getValue(),postId));

        webService.start();
    }

    public static void getPostDetails(String postId,OnWebserviceFinishListener listener){

        WebService webService = getWebService(listener);

        webService.setService(String.format(RequestServices.POST_DETAILS.getValue(),postId));

        webService.start();

    }

    public static void getGroupPermissions(String groupId,OnWebserviceFinishListener listener){

        WebService webService = getWebService(listener);

        webService.setService(String.format(RequestServices.GET_PERMISSIONS.getValue(),groupId));

        webService.start();
    }

    public static void setGroupMembersToStatus(String groupId, GroupUserStatus status, OnWebserviceFinishListener listener){

        WebService webService = getWebService(listener);

        String service = String.format(RequestServices.CHANGE_GROUP_MEMBER_STATUS.getValue(),groupId,status.getStatus()-2);

        webService.setService(service);

        webService.start();

    }

    public static void changeUserStatus(String userId , String groupId , OnWebserviceFinishListener listener){

        WebService webService = getWebService(listener);

        String service = String.format(RequestServices.CHANGE_USER_STATUS.getValue(),groupId,userId);

        webService.setService(service);

        webService.start();

    }

    public static void getStudentProgress(String studentId,OnWebserviceFinishListener listener){

        WebService webService = getWebService(listener);

        webService.setService(String.format(RequestServices.GET_STUDENT_PROGRESS.getValue(),studentId));

        webService.start();

    }

    private static void addRecievers(WebService webService, RecieversModel model){

        RecieverType type = model.getType();

        switch (type){

            case ONLY_ME:

                webService.addParams("receivers["+type.getValue()+"][]", SessionManager.getInstance().getCurrentUser().getId() );

                break;

            case ALL_USERS:

            case ALL_GROUPS:

                webService.addParams("receivers["+type.getValue()+"]","1");

                break;

            case GROUPS:

            case USERS:

                for(int i=0; i < model.getRecipientList().size() ; i++){

                    webService.addParams("receivers["+type.getValue()+"][]",model.getRecipientList().get(i).getId());

                }

                break;

            default:

                webService.addParams("receivers["+type.getValue()+"][]", SessionManager.getInstance().getCurrentUser().getId() );

                break;
        }

    }

    public static void addAssignment(String title,String description,String dudate,boolean lockAfterDueDate,RecieversModel model, OnWebserviceFinishListener listener){

        WebService webService = postWebService(listener);

        webService.setService(RequestServices.ADD_POST.getValue());

        webService.addParams("assignment[title]",title);

        webService.addParams("assignment[description]",description);

        webService.addParams("assignment[duedate]",dudate);

        webService.addParams("assignment[lock]",""+(lockAfterDueDate?1:0));

        addRecievers(webService,model);

        webService.start();
    }

    public static void addNote(String title, String description, RecieversModel model ,OnWebserviceFinishListener listener){

        WebService webService = postWebService(listener);

        webService.setService(RequestServices.ADD_POST.getValue());

        webService.addParams("note[title]",title);

        webService.addParams("note[description]",description);

        addRecievers(webService,model);

        webService.start();

    }

    public static void unlikePost(String postId,OnWebserviceFinishListener listener){

        WebService webService = deleteWebService(listener);

        webService.setService(String.format(RequestServices.UNLIKE_POST.getValue(),postId));

        webService.start();

    }

    public static void votePoll(String postId,String optionId,OnWebserviceFinishListener listener){

        WebService webService = postWebService(listener);

        webService.setService(String.format(RequestServices.VOTE.getValue(),postId));

        webService.addParams("poll_option_id",optionId);

        webService.start();

    }

    public static void addComment(String postId,String comment,OnWebserviceFinishListener listener){

        WebService webService = postWebService(listener);

        webService.setService(String.format(RequestServices.ADD_COMMENT.getValue(),postId));

        webService.addParams("comment",comment);

        webService.start();

    }

    public static void getLibrary(OnWebserviceFinishListener listener){

        WebService webService = getWebService(listener);

        webService.setService(RequestServices.GET_LIBRARY.getValue());

        webService.start();

    }

    public static void getGroupLibrary(String groupId,OnWebserviceFinishListener listener){

        WebService webService = getWebService(listener);

        webService.setService(String.format(RequestServices.GET_GROUP_LIBRARY.getValue(),groupId));

        webService.start();

    }

    public static void likePost(String postId,OnWebserviceFinishListener listener){

        WebService webService = postWebService(listener);

        webService.setService(String.format(RequestServices.LIKE_POST.getValue(),postId));

        webService.start();
    }

    public static void getUserInfo(OnWebserviceFinishListener listener){

        WebService webService = getWebService(listener);

        webService.setService(RequestServices.GET_USER_INFO.getValue());

        webService.start();
    }

    private static void updateToken(final WebService originalWebService){

        String name = SessionManager.getInstance().getUsername();

        String password = SessionManager.getInstance().getPassword();

        final WebService ws = getLoginWebService(name, password, new OnWebserviceFinishListener() {

            @Override
            public void onFinish(WebService webService) {

                try {

                    String token = new JSONObject(webService.getStrResponse().toString()).getString("token");

                    SessionManager.getInstance().saveString("token",token);

                    reExecuteRequest(originalWebService);

                }
                catch (Exception e){

                    e.printStackTrace();

                }

            }

        });

        ws.start();
    }

    private static void reExecuteRequest(WebService webService){

        WebService ws = new WebService(Constants.server);

        ws.setPort(Constants.port);

        ws.setService(webService.getService());

        ws.setOnWebserviceFinishListener(webService.getWebServiceListener());

        ws.setMethod(webService.getMethod());

        ws.addHeader("Authorization","bearer " + SessionManager.getInstance().getToken());

        ws.start();

    }

    private static String getError(String response){

        String error = "";

        try{

            JSONObject jsonError = new JSONObject(response);

            error = jsonError.getString("error");

        }
        catch (Exception e){

            Log.e(TAG,"error in parsing JSONObject ",e);
        }

        return error;
    }

    public static void processWebServiceError(WebService webService){

        String result = webService.getStrResponse().toString();

        String error = getError(result);

        if(error.equalsIgnoreCase(ErrorType.TOKEN_EXPIRED.getValue())){

            updateToken(webService);

        }
    }

    public static void getUserGroups(OnWebserviceFinishListener listener){

        WebService webService = getWebService(listener);

        webService.setService(RequestServices.GET_USER_GROUPS.getValue());

        webService.start();

    }

    public static void getUserRecipients(OnWebserviceFinishListener listener){

        WebService webService = getWebService(listener);

        webService.setService(RequestServices.GET_USER_RECIPIENTS.getValue());

        webService.start();
    }

    public static void getStreamBook(OnWebserviceFinishListener listener){

        WebService webService = getWebService(listener);

        webService.setService(RequestServices.GET_STREAM_BOOK.getValue());

        webService.start();
    }

    private static WebService deleteWebService(OnWebserviceFinishListener listener){

        WebService webService = getWebService(listener);

        webService.setMethod("Delete");

        return webService;

    }

    private static WebService postWebService(OnWebserviceFinishListener listener){

        WebService webService = getWebService(listener);

        webService.setMethod("Post");

        return webService;
    }

    private static WebService getWebService(OnWebserviceFinishListener listener){

        WebService webService = new WebService(Constants.server);

        webService.setPort(Constants.port);

        webService.setMethod("Get");

        webService.addHeader("Authorization","bearer " + SessionManager.getInstance().getToken());

        webService.setOnWebserviceFinishListener(listener);

        return webService;
    }

    private static WebService getLoginWebService(String name,String password,OnWebserviceFinishListener listener){

        final WebService ws = new WebService(Constants.server);

        ws.setVersion("v1");

        ws.setService("authenticate");

        String userType = "email";

        ws.addParams(userType, name);

        ws.addParams("password", password);

        ws.setPort(Constants.port);

        ws.setOnWebserviceFinishListener(listener);

        return ws;

    }

}
