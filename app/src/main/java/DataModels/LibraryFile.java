package DataModels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mac on 6/23/16.
 */
public class LibraryFile implements Serializable {

    String id;

    String type;

    String name;

    @SerializedName("files")
    List<LibraryFile> fileList;


    public List<LibraryFile> getFileList() {

        return fileList;

    }

    public void setFileList(List<LibraryFile> fileList) {

        this.fileList = fileList;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
