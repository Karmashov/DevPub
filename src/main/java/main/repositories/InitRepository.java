package main.repositories;

import main.model.Init;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface InitRepository extends JpaRepository<Init, Integer> {
}
