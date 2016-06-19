package DataModels;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Permession {

    private static final String TAG = Permession.class.getSimpleName();

    List<PermessionRole> permessionRoles = new ArrayList<>();

    Map<Integer,Context> contextMap = new HashMap<>();

    Map<Integer,PermessionName> permessionNames = new HashMap<>();

    Map<String,PermessionRole> permessions = new HashMap<>();

    public Permession(){

        prepareContextMap();

        preparePermessionNames();

        preparePermessionStrings();
    }

    private void preparePermessionStrings(){

        permessions.put("add_comment",PermessionRole.ADD_COMMENT);

        permessions.put("delete_comment",PermessionRole.DELETE_COMMENT);

        permessions.put("edit_comment",PermessionRole.EDIT_COMMENT);

        permessions.put("view_comment",PermessionRole.VIEW_COMMENT);

        permessions.put("add_like",PermessionRole.ADD_LIKE);

        permessions.put("delete_like",PermessionRole.DELETE_LIKE);

        permessions.put("add_post",PermessionRole.ADD_POST);

        permessions.put("delete_post",PermessionRole.DELETE_POST);

        permessions.put("edit_post",PermessionRole.EDIT_POST);

        permessions.put("view_post",PermessionRole.VIEW_POST);

        permessions.put("view_lib",PermessionRole.VIEW_LIBRARY);

        permessions.put("edit_user_roles",PermessionRole.EDIT_USER_ROLES);

    }

    private void preparePermessionNames(){

        permessionNames.put(1,PermessionName.ADD);

        permessionNames.put(2,PermessionName.DELETE);

        permessionNames.put(3,PermessionName.EDIT);

        permessionNames.put(4,PermessionName.VIEW);

    }

    private void prepareContextMap(){

        contextMap.put(1,Context.COMMENT);

        contextMap.put(2,Context.LIKE);

        contextMap.put(3,Context.POST);

        contextMap.put(4,Context.LIBRARY);

        contextMap.put(5,Context.USER_ROLES);

        contextMap.put(6,Context.EXAM_GRADES);
    }

    public void addPermessions(JSONArray roles){

        try {

            for(int i = 0 ; i < roles.length(); i++) {

                JSONObject role = roles.getJSONObject(i);

                int contextId = role.getInt("context_id");

                int permessionId = role.getInt("permission_id");

                Context context = contextMap.get(contextId);

                PermessionName permession = permessionNames.get(permessionId);

                String permessionName = permession.getValue()+context.getValue();

                PermessionRole permessionRole = permessions.get(permessionName);

                if(permessionRole != null) {

                    permessionRoles.add(permessionRole);

                }


            }
        }
        catch (Exception e){

            Log.e(TAG,"error in parsing JSONArray ",e);
        }
    }

    public List<PermessionRole> getPermessionRoles() {
        return permessionRoles;
    }

    private enum Context{

        COMMENT("_comment"),LIKE("_like"),POST("_post"),LIBRARY("_lib"),USER_ROLES("_user_roles"),EXAM_GRADES("exam_grades");

        private final String value;

        Context(String name){

            this.value = name;
        }

        public String getValue(){

            return value;
        }
    }

    private enum PermessionName{

        ADD("add"),DELETE("delete"),EDIT("edit"),VIEW("view");

        private final String value;

        PermessionName(String name){

            this.value = name;
        }

        public String getValue(){

            return value;
        }
    }

    public enum PermessionRole{

        ADD_COMMENT,DELETE_COMMENT,EDIT_COMMENT,VIEW_COMMENT,
        ADD_LIKE,DELETE_LIKE,ADD_POST,DELETE_POST,EDIT_POST,VIEW_POST,
        VIEW_LIBRARY, EDIT_USER_ROLES,
    }
}
