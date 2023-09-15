package com.example.PocektHistory.pocketHistory.firebase;


import com.example.PocektHistory.pocketHistory.user.entity.User;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


@Service
public class FirebaseInitialization {

    private static final String QUESTIONS_COLLECTION_NAME = "questions";
    @PostConstruct
    public void initializaiotn(){

        FileInputStream serviceAccount =
                null;
        try {
            serviceAccount = new FileInputStream("./serviceAccountKey.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);


            Firestore db = FirestoreClient.getFirestore();

            // Sprawdź, czy kolekcja "questions" już istnieje
            if (!collectionExists(db)) {
//                loadQuestionsFromFile(db, "src/main/java/assets/QuestionsPA.txt");
//                loadQuestionsFromFile(db, "src/main/java/assets/QuestionsHP.txt");
//                loadQuestionsFromFile(db, "src/main/java/assets/QuestionsSE.txt");
//                loadQuestionsFromFile(db, "src/main/java/assets/QuestionsSC.txt");
//                loadQuestionsFromFile(db, "src/main/java/assets/QuestionsNC.txt");
//                loadQuestionsFromFile(db, "src/main/java/assets/QuestionsHS.txt");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private boolean collectionExists(Firestore db) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentSnapshot> future = db.collection(QUESTIONS_COLLECTION_NAME).document().get();
        DocumentSnapshot document = future.get();
        return document.exists();
    }

    private void loadQuestionsFromFile(Firestore db, String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    String[] parts = line.split(";");
                    if (parts.length == 4) {
                        String question = parts[0];
                        String type = parts[1];
                        String[] answers = parts[2].substring(1, parts[2].length() - 1).split("\"?,\"?");
                        String correctAnswer = parts[3].substring(1, parts[3].length() - 1);

                        addQuestionToCollection(db, question, type, answers, correctAnswer, QUESTIONS_COLLECTION_NAME);
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addQuestionToCollection(Firestore db, String question, String type, String[] answers,
                                         String correctAnswer, String collectionName) {
        Map<String, Object> questionData = new HashMap<>();
        questionData.put("tresc_pytania", question);
        questionData.put("typ", type);
        questionData.put("odpowiedzi", Arrays.asList(answers));
        questionData.put("poprawna_odpowiedz", correctAnswer);

        try {
            CollectionReference questionsCollection = db.collection(collectionName);
            ApiFuture<DocumentReference> future = questionsCollection.add(questionData);
            DocumentReference questionDocRef = future.get();
            System.out.println("Dodano pytanie o ID: " + questionDocRef.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
