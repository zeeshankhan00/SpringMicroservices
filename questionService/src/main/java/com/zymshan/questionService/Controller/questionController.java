package com.zymshan.questionService.Controller;


import com.zymshan.questionService.Model.questionWrapper;
import com.zymshan.questionService.Model.questions;
import com.zymshan.questionService.Model.quizResponse;
import com.zymshan.questionService.Service.questionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("question")
@CrossOrigin(origins = "http://localhost:3000/")
public class questionController {

    @Autowired
    questionService qService;

     @GetMapping("/allquestions")
    public ResponseEntity<List<questions>> getAllQuestions(){

         try {
             return qService.getAllQuestions();
         } catch (Exception e) {
             e.printStackTrace();
         }
         return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
     }

     @GetMapping("/difficulty/{level}")
    public ResponseEntity<List<questions>> getQuestionsByDifficulty(@PathVariable String level){

         try {
             return qService.getQuestionsByDifficulty(level);
         } catch (Exception e) {
             e.printStackTrace();
         }

         return new ResponseEntity<>(new ArrayList<>(),HttpStatus.OK);

     }

     @PostMapping("/addquestion")
    public ResponseEntity<String> createQuestion(@RequestBody questions q1){

         try {
             return qService.createQuestion(q1);
         } catch (Exception e) {
             e.printStackTrace();
         }

         return new ResponseEntity<>("Failed",HttpStatus.BAD_GATEWAY);
     }



     //Services to be added as a part of Microservices
     //Generate Questions
    //get Questions
    // get Score

    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(
            @RequestParam String title, @RequestParam String category){
         return qService.getQuestionsForQuiz(title,category);
    }

    @PostMapping("/getquestions")
    public ResponseEntity<List<questionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds){
         return qService.getQuestionsFromIds(questionIds);
    }

    @PostMapping("/getscore")
    public ResponseEntity<Integer> getScore(@RequestBody List<quizResponse> response){
         return qService.getScore(response);
    }

}
