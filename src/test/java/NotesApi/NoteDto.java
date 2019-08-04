package NotesApi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NoteDto {

    private final int id;
    private String body;

    @JsonCreator
    public NoteDto(@JsonProperty("id") int id, @JsonProperty("body") String body) {
        this.id = id;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String value) {
        this.body = value;
    }
}