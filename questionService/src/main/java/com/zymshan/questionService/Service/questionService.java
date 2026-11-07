package com.zymshan.questionService.Service;



import com.zymshan.questionService.Model.questionWrapper;
import com.zymshan.questionService.Model.questions;
import com.zymshan.questionService.Model.quizResponse;
import com.zymshan.questionService.Repository.questionRepo;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class questionService {

    @Autowired
    public questionRepo qRepo;

    public ResponseEntity<List<questions>> getAllQuestions(){
        return new ResponseEntity<>(qRepo.findAll(),HttpStatus.OK);
    }

    public ResponseEntity<List<questions>> getQuestionsByDifficulty(String difficulty){
        return new ResponseEntity<>(qRepo.findByDifficulty(difficulty),HttpStatus.OK);
    }


    public ResponseEntity<String> createQuestion(questions q1) {
         qRepo.save(q1);
         return new ResponseEntity<>("Success",HttpStatus.CREATED);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String title, String category) {
        List<Integer> questionIds = qRepo.findByCategory(category);

        return new ResponseEntity<>(questionIds, HttpStatus.OK);
    }

    public ResponseEntity<List<questionWrapper>> getQuestionsFromIds(List<Integer> questionIds) {

        List<questions> questions = qRepo.findAllById(questionIds);

        List<questionWrapper> questionForUsers = new ArrayList<>();

        for(questions q : questions){
            questionForUsers.add(new questionWrapper(q.getId(), q.getQuestion(), q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4()));
        }

        return new ResponseEntity<>(questionForUsers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<quizResponse> response) {
        List<questions> questionsList = qRepo.findAll();
        Integer result = 0;

        Map<Integer,String> answerMap = new HashMap<>();

        for(questions q : questionsList){
            answerMap.put(q.getId(),q.getRightAnswer());
        }

        for(quizResponse quizResponse : response){
            if(answerMap.containsKey(quizResponse.getId())){
                if(quizResponse.getUserResponse().equalsIgnoreCase(answerMap.get(quizResponse.getId()))){
                    result += 1;
                }

                else {
                    result -= 1;
                }
            }
        }

        return new ResponseEntity<>(result,HttpStatus.OK);

    }
}
