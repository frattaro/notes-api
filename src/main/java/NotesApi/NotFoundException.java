package NotesApi;

import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Note not found.")
public final class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public  NotFoundException() {
        super();
    }

    public  NotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public  NotFoundException(final String message) {
        super(message);
    }

    public  NotFoundException(final Throwable cause) {
        super(cause);
    }
}