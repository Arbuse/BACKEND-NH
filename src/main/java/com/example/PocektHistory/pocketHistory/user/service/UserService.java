package com.example.PocektHistory.pocketHistory.user.service;


import com.example.PocektHistory.pocketHistory.user.entity.Authorization;
import com.example.PocektHistory.pocketHistory.user.entity.Product;
import com.example.PocektHistory.pocketHistory.user.entity.User;
import com.example.PocektHistory.pocketHistory.user.models.UserEditApi;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.FirestoreClient;
import io.grpc.Status;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;


@Service
public class UserService {

    private static final String COLLECTION_NAME = "users";
    private static final String AUTHORIZATION_COLLECTION = "authorization";


    public User getUserById(Long userId) throws FirestoreException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COLLECTION_NAME).document(String.valueOf(userId));

        ApiFuture<DocumentSnapshot> future = documentReference.get();

        try {
            DocumentSnapshot document = future.get();
            User user;
            if (document.exists()) {
                user = document.toObject(User.class);
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new FirestoreCustomException("Failed to get user by ID.",e);
        }
    }

    public Authorization getAuthorizationById(Long userId) throws FirestoreException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(AUTHORIZATION_COLLECTION).document(String.valueOf(userId));

        ApiFuture<DocumentSnapshot> future = documentReference.get();

        try {
            DocumentSnapshot document = future.get();
            Authorization authorization;
            if (document.exists()) {
                authorization = document.toObject(Authorization.class);
                return authorization;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new FirestoreCustomException("Failed to get authorization by ID.", e);
        }
    }


    public List<User> getUsers() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference usersCollection = dbFirestore.collection(COLLECTION_NAME);

        ApiFuture<QuerySnapshot> future = usersCollection.get();

        QuerySnapshot querySnapshot = future.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        List<User> users = new ArrayList<>();

        for (QueryDocumentSnapshot document : documents) {
            User user = document.toObject(User.class);
            users.add(user);
        }

        return users;
    }

    public List<User> ranking() throws ExecutionException, InterruptedException {
        List<User> users = getUsers();

        users.sort(Comparator.comparing(User::getPoints).reversed());

        return users;
    }

    public void updateUser(UserEditApi userEditApi) throws ExecutionException, InterruptedException {
        User user = getUserById(userEditApi.getUserId());
        update(userEditApi, user);
        Firestore dbFirestore = FirestoreClient.getFirestore();
        dbFirestore.collection(COLLECTION_NAME).document(String.valueOf(userEditApi.getUserId())).set(user);

        //return collectionApiFuture.get().getUpdateTime().toString();
    }

    public boolean authorization(String password, String email, Long userId) {
        Authorization authorization = getAuthorizationById(userId);
        return authorization != null && password.equals(authorization.getPassword()) && email.equals(authorization.getEmail());
    }



    public static class FirestoreCustomException extends RuntimeException {
        public FirestoreCustomException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static UserEditApi convertToApi(User user) {
        UserEditApi userEditApi = new UserEditApi();
        userEditApi.setUsername(user.getUsername());
        userEditApi.setAvatar(user.getAvatar());
        userEditApi.setDesc(user.getDesc());
        return userEditApi;
    }

    private static User update(UserEditApi userEditApi,User user) {
        user.setUsername(userEditApi.getUsername());
        user.setAvatar(userEditApi.getAvatar());
        user.setDesc(userEditApi.getDesc());
        return user;
    }
}
