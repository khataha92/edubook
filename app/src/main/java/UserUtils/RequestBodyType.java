package UserUtils;

/**
 * Created by mohammad on 4/19/15.
 * The enum that contain the request body types
 */
public enum RequestBodyType {
    XML, JSON;

    @Override
    public String toString() {

        if (this == XML) {
            return "application/xml;charset=UTF-8";
        }

        if (this == JSON) {
            return "application/json;charset=UTF-8";
        }

        return super.toString();
    }
}
