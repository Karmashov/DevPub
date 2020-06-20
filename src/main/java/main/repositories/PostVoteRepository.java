package main.repositories;

import main.model.PostVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PostVoteRepository extends JpaRepository<PostVote,Integer> {
}
