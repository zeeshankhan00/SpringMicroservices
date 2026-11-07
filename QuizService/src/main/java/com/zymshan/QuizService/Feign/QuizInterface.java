package com.zymshan.QuizService.Feign;

import com.zymshan.QuizService.Model.questionWrapper;
import com.zymshan.QuizService.Model.quizResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTIONSERVICE")
public interface QuizInterface {
    @GetMapping("question/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(
            @RequestParam String title, @RequestParam String category);

    @PostMapping("question/getquestions")
    public ResponseEntity<List<questionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds);

    @PostMapping("question/getscore")
    public ResponseEntity<Integer> getScore(@RequestBody List<quizResponse> response);
}
