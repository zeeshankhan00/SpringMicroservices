package com.zymshan.QuizService.Service;

import com.zymshan.QuizService.Feign.QuizInterface;
import com.zymshan.QuizService.Model.questionWrapper;
import com.zymshan.QuizService.Model.quiz;
import com.zymshan.QuizService.Model.quizResponse;
import com.zymshan.QuizService.Repository.quizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service

public class quizService {

    @Autowired
    quizRepo quizRepo;

    @Autowired
    QuizInterface quizInterface;



    public ResponseEntity<Integer> createQuiz(String title, String category){

       Integer quizId;

       // call the question/generate method to generate the questions from "question-service" Service.
       // call the url -> http://localhost:8080/question/generate -> RestTemplate (Instead of RestTemplate we will be using Feign CLient)

       List<Integer> questions = quizInterface.getQuestionsForQuiz(title,category).getBody();

       quiz quiz = new quiz();
       quiz.setTitle(title);
       quiz.setQuestionIds(questions);

       quiz createdQuiz = quizRepo.save(quiz);

       quizId = createdQuiz.getId();

       return new ResponseEntity<>(quizId,HttpStatus.OK);
    }

    public ResponseEntity<List<questionWrapper>> getQuizQuestions(String quizId) {

       /* Optional<quiz> quiz = quizRepo.findById(Integer.valueOf(id));
        List<questions> questionsFromDB = quiz.get().getQuestionsList();*/



        Optional<quiz> quiz = quizRepo.findById(Integer.valueOf(quizId));
        List<Integer> questionsFromDB = quiz.get().getQuestionIds();

        List<questionWrapper> questionForUsers = quizInterface.getQuestionsFromId(questionsFromDB).getBody();

        return new ResponseEntity<>(questionForUsers,HttpStatus.OK);

       /* for(questions q : questionsFromDB){
            questionWrapper qw = new questionWrapper(q.getId(), q.getQuestion(),q.getOption1(),q.getOption2(), q.getOption3(), q.getOption4());
            questionForUsers.add(qw);
        }*/

    }

    public ResponseEntity<Integer> calculateResponse(String quizId, List<quizResponse> respones) {
        Integer result = quizInterface.getScore(respones).getBody();

       /* Optional<quiz> quiz = quizRepo.findById(Integer.valueOf(quizId));
        List<questions> questionsFromDB = quiz.get().getQuestionsList();

        Map<Integer,String> questionAnwerMap = new HashMap<>();

        for(questions q : questionsFromDB){
            questionAnwerMap.put(q.getId(),q.getRightAnswer());
        }

        for(quizResponse quizResponse : respones){
            Integer questionId = quizResponse.getId();
            if(questionAnwerMap.containsKey(questionId)){
                if(questionAnwerMap.get(questionId).equalsIgnoreCase(quizResponse.getUserResponse())){
                    result += 1;
                }
                else{
                    result -= 1;
                }
            }
        }*/



        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
