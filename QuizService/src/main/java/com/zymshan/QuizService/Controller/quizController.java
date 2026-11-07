package com.zymshan.QuizService.Controller;

import com.zymshan.QuizService.Model.QuizDto;
import com.zymshan.QuizService.Model.questionWrapper;
import com.zymshan.QuizService.Model.quizResponse;
import com.zymshan.QuizService.Service.quizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
@CrossOrigin(origins = "http://localhost:3000/")
public class quizController {

    @Autowired
    quizService quizService;



    @PostMapping("/create")
    public ResponseEntity<Integer> createQuiz(@RequestBody QuizDto quizDto){

        try{
            return quizService.createQuiz(quizDto.getTitle(),quizDto.getCategory());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(-1, HttpStatus.BAD_GATEWAY);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<questionWrapper>> getQuizQuestions(@PathVariable String id){
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> getQuizScore(@PathVariable String id, @RequestBody List<quizResponse> respones){
        return quizService.calculateResponse(id,respones);
    }
}
