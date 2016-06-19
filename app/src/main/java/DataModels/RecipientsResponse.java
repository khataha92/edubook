package DataModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lap on 6/10/16.
 */
public class RecipientsResponse implements Serializable {

    List<Group> groups;

    List<User> students;

    List<User> contacts;

    List<User> parents;

    List<User> onlyme;

    public List<Group> getGroups() {

        List<Group> group = new ArrayList<>();

        return groups != null ? groups : group;

    }

    public List<User> getContacts() {

        List<User> contacts = new ArrayList<>();

        if(this.contacts != null){

            return this.contacts;
        }
        return contacts;
        //return this.contacts!=null?this.contacts : contacts;

    }

    public List<User> getOnlyme() {

        return onlyme;

    }

    public List<User> getParents() {

        return parents;

    }

    public List<User> getStudents() {

        List<User> student = new ArrayList<>();

        return students != null ? students : student;

    }
}
