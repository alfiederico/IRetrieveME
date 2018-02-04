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

    public Message(){

    }

    public Message(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

}
