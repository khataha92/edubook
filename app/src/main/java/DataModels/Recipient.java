package DataModels;

/**
 * Created by lap on 3/16/16.
 */
public class Recipient {

    RecipientType type;

    boolean removableFromTags = true;

    String id;

    String name;

    String image;

    public void setRemovableFromTags(boolean removableFromTags){

        this.removableFromTags = removableFromTags;

    }

    public boolean isRemovableFromTags(){

        return removableFromTags;

    }

    public Recipient(RecipientType type, String id, String name) {

        this.type = type;

        this.id = id;

        this.name = name;

    }

    public String getImage() {

        return image;

    }

    public void setImage(String image) {

        this.image = image;

    }

    public RecipientType getType() {

        return type;

    }

    public void setType(RecipientType type) {

        this.type = type;

    }

    public String getId() {

        if(id.indexOf(".") != -1){

            id = id.substring(0,id.indexOf("."));

        }

        return id;

    }

    public void setId(String id) {

        this.id = id;

    }

    public String getName() {

        return name;

    }

    public void setName(String name) {

        this.name = name;

    }
    public String toString(){

        return name;

    }

    public enum RecipientType{

        GROUP,STUDENT,CONTACT,ONLY_ME
    }
}
