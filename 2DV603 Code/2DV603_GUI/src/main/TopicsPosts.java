package main;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by benjamin on 2017-05-26.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TopicsPosts {

    @JsonProperty("recent")
    private List<JodelPost> recent;

    public List<JodelPost> getRecent() {
        return recent;
    }

    public void setRecent(List<JodelPost> recent) {
        this.recent = recent;
    }
}
