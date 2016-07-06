package DataModels;

import java.io.Serializable;

import Managers.SessionManager;

/**
 * Created by lap on 6/10/16.
 */
public class User implements Serializable{

    String id,name,thumb,email;

    UserType type;

    Role role;

    public void setRole(Role role) {

        this.role = role;

    }

    public Role getRole() {

        return role;

    }

    public String getId() {

        return id;

    }

    public String getName() {

        return name;

    }

    public String getThumb() {

        return thumb + "?token="+ SessionManager.getInstance().getString("token");

    }

    public UserType getType() {

        return type;

    }

    public void setEmail(String email) {

        this.email = email;

    }

    public String getEmail() {

        return email;

    }

    public void setId(String id) {

        this.id = id;

    }

    public void setName(String name) {

        this.name = name;

    }

    public void setThumb(String thumb) {

        this.thumb = thumb;

    }

    public void setType(UserType type) {

        this.type = type;

    }

    public class UserType{

        String name;

        public void setName(String name) {

            this.name = name;

        }

        public String getName() {

            return name;

        }
    }
}
