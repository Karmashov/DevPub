package main.repositories;

import main.model.Tag2Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface Tag2PostRepository extends JpaRepository<Tag2Post,Integer> {
}
