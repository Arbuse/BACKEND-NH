package com.example.PocektHistory.pocketHistory.questions.service;

import com.example.PocektHistory.pocketHistory.questions.entity.Question;
import com.example.PocektHistory.pocketHistory.user.entity.User;
import com.example.PocektHistory.pocketHistory.user.service.UserService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class QuestionService {

    private static final String COLLECTION_NAME = "questions";

    private static final UserService userService = new UserService();


    public List<Question> getQuestions() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference usersCollection = dbFirestore.collection(COLLECTION_NAME);

        ApiFuture<QuerySnapshot> future = usersCollection.get();

        QuerySnapshot querySnapshot = future.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        List<Question> questions = new ArrayList<>();

        for (QueryDocumentSnapshot document : documents) {
            Question question = document.toObject(Question.class);
            questions.add(question);
        }

        return questions;
    }

    public List<Question> getQuestionsByTyp(String typ) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference questionsCollection = dbFirestore.collection(COLLECTION_NAME);

        Query query = questionsCollection.whereEqualTo("typ", typ);
        ApiFuture<QuerySnapshot> future = query.get();

        QuerySnapshot querySnapshot = future.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        List<Question> questions = new ArrayList<>();

        for (QueryDocumentSnapshot document : documents) {
            Question question = document.toObject(Question.class);
            questions.add(question);
        }

        return questions;
    }
    public List<Question> getQuestionsRandomize(int count) throws ExecutionException, InterruptedException {
        List<Question> questions = getQuestions();
        return randomizeQuestion(questions, count);
    }

    public List<Question> getQuestionsByTypRandomize(String typ, int count) throws ExecutionException, InterruptedException {
        List<Question> questions = getQuestionsByTyp(typ);
        return randomizeQuestion(questions,count );
    }

    private List<Question> randomizeQuestion(List<Question> questions, int count) {
        if (count >= questions.size()) {
            return questions;
        }

        List<Question> randomizedQuestions = new ArrayList<>(questions);
        Collections.shuffle(randomizedQuestions);

        return randomizedQuestions.subList(0, count);
    }

    public List<Question> getQuestionsInLearningMode(Long userId) throws ExecutionException, InterruptedException {
        List<Question> questions = getQuestions();

        return learningMode(questions,userId);
    }

//    private List<Question> learningMode(List<Question> questions, Long userId) {
//        User user = userService.getUserById(userId);
//
//        List<Question> learningQuestions = new ArrayList<>();
//        Random random = new Random();
//        int minQuestions = 10; // Minimalna liczba pytań
//        int maxQuestions = 15; // Maksymalna liczba pytań
//
//        int addedQuestions = 0; // Licznik dodanych pytań
//
//        // Sprawdź, czy liczba dostępnych pytań jest mniejsza niż minimalna liczba
//        if (questions.size() < minQuestions) {
//            return questions; // Jeśli liczba dostępnych pytań jest mniejsza niż minimalna liczba, zwróć wszystkie pytania
//        }
//
//        while (addedQuestions < maxQuestions) {
//            Question randomQuestion = questions.get(random.nextInt(questions.size()));
//            String typ = randomQuestion.getTyp();
//            Long learningFactor = getLearningFactor(user, typ);
//            double inverseLearningFactor = 1.0 / learningFactor;
//
//            double randomValue = random.nextDouble();
//            if (randomValue < inverseLearningFactor) {
//                learningQuestions.add(randomQuestion);
//                addedQuestions++; // Zwiększ licznik dodanych pytań
//            }
//        }
//
//        return learningQuestions;
//    }

    private List<Question> learningMode(List<Question> questions, Long userId) {
        User user = userService.getUserById(userId);

        List<Question> learningQuestions = new ArrayList<>();
        Random random = new Random();
        int numberOfQuestions = 10; // Pytania do wylosowania
        int totalQuestions = questions.size();

        List<Integer> questionIndices = new ArrayList<>();
        for (int i = 0; i < totalQuestions; i++) {
            questionIndices.add(i);
        }

        Collections.shuffle(questionIndices); // Mieszaj indeksy pytań

        int currentIndex = 0;

        while (learningQuestions.size() < numberOfQuestions) {
            if (currentIndex >= totalQuestions) {
                currentIndex = 0; // Jeśli osiągnięto koniec listy, zacznij od początku
                Collections.shuffle(questionIndices); // Ponownie zamieszaj indeksy pytań
            }

            int randomIndex = questionIndices.get(currentIndex);
            Question randomQuestion = questions.get(randomIndex);
            String typ = randomQuestion.getTyp();
            Long learningFactor = getLearningFactor(user, typ);
            double inverseLearningFactor = 1.0 / learningFactor;

            double randomValue = random.nextDouble();
            if (randomValue < inverseLearningFactor) {
                learningQuestions.add(randomQuestion);
            }

            currentIndex++;
        }

        return learningQuestions;
    }

    public String testLM(List<Question> learningQuestions, Long userId) {
        User user = userService.getUserById(userId);
        Map<String, Integer> questionCountByFactor = new HashMap<>();

        for (Question question : learningQuestions) {
            String type = question.getTyp();
            Long learningFactor = getLearningFactor(user, type);
            String factorKey = "wspolczynnik-" + type;

            questionCountByFactor.put(factorKey, questionCountByFactor.getOrDefault(factorKey, 0) + 1);
        }

        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Integer> entry : questionCountByFactor.entrySet()) {
            String factor = entry.getKey();
            Integer count = entry.getValue();
            result.append(factor).append(" = ").append(count).append("\n");
        }

        return result.toString();
    }


    private Long getLearningFactor(User user, String typ) {
        switch (typ) {
            case "HP":
                return user.getHP_learningFactor();
            case "HS":
                return user.getHS_learningFactor();
            case "NC":
                return user.getNC_learningFactor();
            case "PA":
                return user.getPA_learningFactor();
            case "SC":
                return user.getSC_learningFactor();
            case "SE":
                return user.getSE_learningFactor();
            default:
                return 1L;
        }
    }


}
