package main;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by benjamin on 2017-05-26.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepliesPosts {

    @JsonProperty("details")
    private JodelPost details;

    @JsonProperty("replies")
    private List<JodelPost> replies;

    public List<JodelPost> getReplies(){
        return replies;
    }
    public void setReplies(List<JodelPost> replies) {
        this.replies = replies;
    }

    public JodelPost getDetails() {
        return details;
    }

    public void setDetails(JodelPost details) {
        this.details = details;
    }
}
