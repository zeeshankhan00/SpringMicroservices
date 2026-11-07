package com.zymshan.questionService.Repository;

import com.zymshan.questionService.Model.questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface questionRepo extends JpaRepository<questions,Integer> {

    List<questions> findByDifficulty(String difficulty);

    @Query(value="Select q.id FROM questions q Where q.category =:category")
    List<Integer> findByCategory(@Param("category") String category);
}
