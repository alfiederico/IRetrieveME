package cloud.iretrieve.com.iretrieve.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Alfie on 07/01/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    @JsonProperty("id")
    private  long id;
    @JsonProperty("content")
    private  String content;

    @JsonProperty("radius")
    private  int radius;

    public Message(){

    }

    public Message(long id, String content, int radius) {
        this.id = id;
        this.content = content;
        this.radius = radius;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getRadius() {
        return radius;
    }

}
