package NotesApi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;

public class BlackBoxTest {
    private static final String RESOURCE_URL = "http://localhost:8080/api/notes";
    private static final String NOTE_BODY = "POSTed note body.";
    private static final String NOTE_BODY2 = "PUTed note body.";
    private Process process = null;
    private RestTemplate restTemplate = null;
    
    @Before
    public void before() throws Exception {
        process = new ProcessExecutor().execute();
        restTemplate = new RestTemplate();
        waitForStart(restTemplate);
    }
     
    @Test
    public void testPost() {
        HttpEntity<String> request = new HttpEntity<>(NOTE_BODY);
        NoteDto actualNote = restTemplate.postForObject(RESOURCE_URL, request, NoteDto.class);
        assertEquals(NOTE_BODY, actualNote.getBody());
    }

    @Test
    public void testGetAll() {
        HttpEntity<String> request = new HttpEntity<>(NOTE_BODY);
        restTemplate.postForObject(RESOURCE_URL, request, NoteDto.class);

        NoteDto[] actualNotes = restTemplate.getForObject(RESOURCE_URL, NoteDto[].class);
        assertTrue(actualNotes.length > 0);
    }
     
    @Test
    public void testGetOneHappy() {
        HttpEntity<String> request = new HttpEntity<>(NOTE_BODY);
        int id = restTemplate.postForObject(RESOURCE_URL, request, NoteDto.class).getId();

        NoteDto actualNote = restTemplate.getForObject(RESOURCE_URL + "/" + id, NoteDto.class);
        assertEquals(id, actualNote.getId());
        assertEquals(NOTE_BODY, actualNote.getBody());
    }
     
    @Test
    public void testGetOneUnHappy() {
    	try {
    		restTemplate.getForObject(RESOURCE_URL + "/-1", NoteDto.class);
    	}
    	catch (HttpClientErrorException ex) {
    		assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    	}

    	try {
    		restTemplate.getForObject(RESOURCE_URL + "/abc", NoteDto.class);
    	}
    	catch (HttpClientErrorException ex) {
    		assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    	}
    }
     
    @Test
    public void testPut() {
        HttpEntity<String> request = new HttpEntity<>(NOTE_BODY);
        int id = restTemplate.postForObject(RESOURCE_URL, request, NoteDto.class).getId();

        HttpEntity<String> request2 = new HttpEntity<>(NOTE_BODY2);
        restTemplate.put(RESOURCE_URL + "/" + id, request2);

        NoteDto actualNote = restTemplate.getForObject(RESOURCE_URL + "/" + id, NoteDto.class);
        assertEquals(id, actualNote.getId());
        assertEquals(NOTE_BODY2, actualNote.getBody());
    }
     
    @Test
    public void testDelete() {
        HttpEntity<String> request = new HttpEntity<>(NOTE_BODY);
        int id = restTemplate.postForObject(RESOURCE_URL, request, NoteDto.class).getId();

        restTemplate.delete(RESOURCE_URL + "/" + id);
        
    	try {
    		restTemplate.getForObject(RESOURCE_URL + "/" + id, NoteDto.class);
    	}
    	catch (HttpClientErrorException ex) {
    		assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    	}
    }
     
    @After
    public void after() throws Exception {
        process.destroyForcibly();
    }
     
    private void waitForStart(RestTemplate restTemplate) {
        while (true) {
            try {
                Thread.sleep(500);
                restTemplate.getForObject(RESOURCE_URL, String.class);
                return;
            } catch (Throwable throwable) {
                // ignoring errors
            }
        }
    }
}