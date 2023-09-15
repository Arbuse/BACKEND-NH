package com.example.PocektHistory.pocketHistory.questions.controller;

import com.example.PocektHistory.pocketHistory.questions.entity.Question;
import com.example.PocektHistory.pocketHistory.questions.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/question")
public class QuestionsController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/getAll")
    public List<Question> getQuestions() throws  ExecutionException, InterruptedException{
        return questionService.getQuestions();
    }

    @GetMapping("/getAllbyTyp/{typ}")
    public List<Question> getQuestionsByTyp(@PathVariable String typ) throws ExecutionException, InterruptedException{
        return questionService.getQuestionsByTyp(typ);
    }

    @GetMapping("/getAllRandomize")
    public List<Question> getQuestionsRandomize(@RequestParam int count) throws  ExecutionException, InterruptedException{
        return questionService.getQuestionsRandomize(count);
    }

    @GetMapping("/getAllbyTypRandomizeRandomize")
    public List<Question> getQuestionsByTypRandomize(@RequestParam String typ, @RequestParam int count) throws ExecutionException, InterruptedException{
        return questionService.getQuestionsByTypRandomize(typ, count);
    }

        @GetMapping("/getQuestionFromLearnigMode")
    public List<Question> getQuestionsInLearningMode(@RequestParam Long userId) throws ExecutionException, InterruptedException {
        return questionService.getQuestionsInLearningMode(userId);
    }

    @GetMapping("/testLM")
    public String testLM(@RequestParam Long userId) throws ExecutionException, InterruptedException {
        return questionService.testLM(questionService.getQuestionsInLearningMode(userId),userId);
    }
}
