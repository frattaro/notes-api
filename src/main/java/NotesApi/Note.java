package NotesApi;

public class Note {

    private final int id;
    private String body;

    public Note(int id, String body) {
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