package hellojpa.inheritence;

import jakarta.persistence.Entity;

@Entity
public class Book extends Item{
    String isbn;
}
