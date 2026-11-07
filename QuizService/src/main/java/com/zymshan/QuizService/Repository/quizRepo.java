package com.zymshan.QuizService.Repository;

import com.zymshan.QuizService.Model.questionWrapper;
import com.zymshan.QuizService.Model.quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface quizRepo extends JpaRepository<quiz,Integer> {

}
