package zxc.studio.vpt.models;

import java.util.Date;

public class Suggestion {

    private String suggestion_sender;
    private String suggestion_body;
    private Date suggestion_dateSent;

    public Suggestion(){}

    public Suggestion(String sender, String body, Date date){
        suggestion_sender = sender;
        suggestion_body = body;
        suggestion_dateSent = date;
    }

    public String getSuggestion_sender() {
        return suggestion_sender;
    }
    public void setSuggestion_sender(String suggestion_sender) {
        this.suggestion_sender = suggestion_sender;
    }
    public String getSuggestion_body() {
        return suggestion_body;
    }
    public void setSuggestion_body(String suggestion_body) {
        this.suggestion_body = suggestion_body;
    }
    public Date getSuggestion_dateSent() {
        return suggestion_dateSent;
    }
    public void setSuggestion_dateSent(Date suggestion_dateSent) {
        this.suggestion_dateSent = suggestion_dateSent;
    }

}
