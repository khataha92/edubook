package DataModels;

import java.util.Objects;

import Enums.HolderType;

/**
 * Created by lap on 6/12/16.
 */
public class PostDataContainer {

    private HolderType type;

    private Object value;

    public HolderType getType() {

        return type;

    }

    public PostDataContainer setType(HolderType type) {

        this.type = type;

        return this;

    }

    public Object getValue() {

        return value;

    }

    public void setValue(Object value) {

        this.value = value;

    }
}
