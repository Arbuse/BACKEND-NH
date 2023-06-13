package com.example.PocektHistory.pocketHistory.user.service;

import com.example.PocektHistory.pocketHistory.user.entity.Authorization;
import com.example.PocektHistory.pocketHistory.user.entity.User;
import com.example.PocektHistory.pocketHistory.user.models.UserRegister;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;


@Service
public class UserCreateService {
    private static final String USERS_COLLECTION = "users";
    private static final String AUTHORIZATION_COLLECTION = "authorization";

    private static final String USER_ID_FIELD = "userId";



    public void createUser(UserRegister userRegister) throws Exception {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        // Generuj nowy userId jako UUID
        UUID uuid = UUID.randomUUID();
        Long newUserId = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();

        // Utwórz nowego użytkownika w kolekcji users
        DocumentReference newUserRef = dbFirestore.collection(USERS_COLLECTION).document(newUserId.toString());
        User user = new User(userRegister.getUsername(), newUserId, userRegister.getFirstName(), userRegister.getLastName(),
                0, userRegister.getAvatar(), userRegister.getDesc(), 0, userRegister.getEmail(),0L,0L,0L,0L,0L,0L);
        ApiFuture<WriteResult> userWriteResult = newUserRef.set(user);

        // Utwórz nowego użytkownika w kolekcji authorization
        DocumentReference newAuthRef = dbFirestore.collection(AUTHORIZATION_COLLECTION).document(newUserId.toString());
        Authorization authorization = new Authorization(userRegister.getPassword(), userRegister.getEmail(),newUserId);
        ApiFuture<WriteResult> authWriteResult = newAuthRef.set(authorization);

        // Poczekaj na zakończenie zapisu do obu kolekcji
        userWriteResult.get();
        authWriteResult.get();

        System.out.println("Użytkownik został pomyślnie utworzony z ID: " + newUserId);
    }
}

