package giorgiaipsaropassione.ParkIT.exceptions;

import org.aspectj.weaver.ast.Not;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {super("Element with id: " + id + "NOT FOUND");}
    public NotFoundException(String message){super(message);}
}
