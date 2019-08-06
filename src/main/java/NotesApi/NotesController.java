package NotesApi;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/notes")
public class NotesController {
    
	private List<Note> notes = new ArrayList<>();

    @GetMapping
    public List<Note> findAll(@RequestParam(value="query", required=false) String query) {
    	if (query != null) {
    		return this.notes.stream()
				.filter(x -> x.getBody().contains(query))
				.collect(Collectors.toList());
    	}
    	
        return this.notes;
    }

    @GetMapping(value = "/{id}")
    public Note findById(@PathVariable("id") int id) {
    	Note note = this.notes.stream()
    	  .filter(x -> x.getId() == id)
    	  .findAny()
    	  .orElse(null);
    	  
    	if (note == null) {
    		throw new NotFoundException();
    	}
    	
    	return note;
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Note add(@RequestBody String body) {
    	int newId = this.notes.stream().mapToInt(x -> x.getId()).max().orElse(0) + 1;
    	Note note = new Note(newId, body);
    	this.notes.add(note);
    	return note;
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") int id, @RequestBody String body) {
    	Note note = this.notes.stream()
    	  .filter(x -> x.getId() == id)
    	  .findAny()
    	  .orElse(null);
    	
    	if (note != null) {
      	  note.setBody(body);
    	}
    }
    
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") int id) {
    	this.notes.removeIf(x -> x.getId() == id);
    }
}