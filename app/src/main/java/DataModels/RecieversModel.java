package DataModels;

import java.util.List;

import Enums.RecieverType;

/**
 * Created by lap on 6/15/16.
 */
public class RecieversModel {

    List<Recipient> recipientList;

    RecieverType type;

    public void setRecipientList(List<Recipient> recipientList) {

        this.recipientList = recipientList;

    }

    public void setType(RecieverType type) {

        this.type = type;

    }

    public List<Recipient> getRecipientList() {

        return recipientList;

    }

    public RecieverType getType() {

        return type;

    }
}
