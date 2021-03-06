package Enums;

/**
 * Created by lap on 6/15/16.
 */
public enum RequestServices {

    CHANGE_USER_PROFILE("/api/v1/auth/image"),
    SET_NOTIFICATION_OPENED("notifications/%s/opened"),
    GET_USER_NOTIFICATIONS("notifications"),
    GET_NOTIFICATION_COUNT("notifications/count"),
    GET_PARENT_ACCESS_KEY("parentkey/%s"),
    REMOVE_PROFILE_IMAGE("auth/profileImage/%s"),
    CHANGE_USER_PASSWORD("auth/password"),
    ADD_POST("post"),
    GET_POST_LIKES("post/%s/like"),
    GET_GROUP_STREAM("group/%s/posts"),
    DELETE_POST("post/%s"),
    POST_DETAILS("post/%s"),
    GET_PERMISSIONS("role/simple/%s"),
    CHANGE_GROUP_MEMBER_STATUS("group/%s/readonly/%s"),
    CHANGE_USER_STATUS("member/%s/readonly/%s"),
    UNLIKE_POST("post/%s/like"),
    VOTE("post/%s/vote"),
    ADD_COMMENT("post/%s/comment"),
    GET_LIBRARY("user/library"),
    LIKE_POST("post/%s/like"),
    GET_USER_INFO("authenticate/user"),
    GET_USER_GROUPS("group"),
    GET_USER_RECIPIENTS("recipients"),
    GET_STREAM_BOOK("post?page=%s"),
    CHANGE_USER_EMAIL("auth/%s"),
    GET_GROUP_LIBRARY("group/%s/library"),
    EDIT_GROUP_DESCRIPTION("group/%s"),
    GET_GROUP_MEMBERS("group/%s/member"),
    REMOVE_USER_FROM_GROUP("group/%s/member/%s"),
    GET_STUDENT_PROGRESS("student/%s/progress"),
    YOUTUBE_DETAILS("https://www.youtube.com/oembed?url=%s&format=json")
    ;

    private String value;

    RequestServices(String service){

        this.value = service;
    }

    public String getValue() {

        return value;

    }
}
