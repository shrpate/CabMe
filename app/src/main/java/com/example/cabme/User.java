package com.example.cabme;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *  User class meant to pull and provide information to the FireBase database about the users
 *  involved in the CabMe application
 */
public class User extends CModel implements Serializable {
    final  private String TAG = "User";
    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private String uid;
    private String phone;
    //private int balance;
    private transient FirebaseFirestore db;
    private transient CollectionReference collectionReference;

    /**
     * This constructor is for users that are logging in or getting information of other
     * user involved in the current request
     *
     * @param uid
     */
    User (String uid) {
        db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("users");
        this.uid = uid;

        collectionReference
                .document(uid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d(TAG, "Data retrieval successful");

                        email = documentSnapshot.getString("email");
                        firstName = documentSnapshot.getString("first");
                        lastName = documentSnapshot.getString("last");
                        username = documentSnapshot.getString("username");
                        phone = documentSnapshot.getString("phone");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Data retrieval failed " + e.toString());
                    }
                });
        setDocumentListener(uid);
    }

    /**
     * This constructor is for users that are signing up for the CabMe application
     *
     * @param uid
     * @param email
     * @param firstName
     * @param lastName
     * @param username
     * @param phone
     */
    User (String uid, String email, String firstName, String lastName, String username, String phone) {

        db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("users");
        this.uid = uid;

        Map<String, Object> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("first", firstName);
        userData.put("last", lastName);
        userData.put("username", username);
        userData.put("phone", phone);

        collectionReference
                .document(uid)
                .set(userData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Data addition successful");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Data addition failed "+ e.toString());
                    }
                });

        setDocumentListener(uid);

    }

    /**
     * This method sets a listener to the user's document in the database to retrieve real-time
     * updates from the database
     *
     * @param uid
     */
    public void setDocumentListener(String uid) {
        collectionReference.document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot == null) {
                    return;
                }

                username = documentSnapshot.getString("username");
                firstName = documentSnapshot.getString("first");
                lastName = documentSnapshot.getString("last");
                email = documentSnapshot.getString("email");
                phone = documentSnapshot.getString("phone");

                notifyViews();
            }
        });

    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    public String getUid() {
        return uid;
    }

    public void setEmail(String email) {
        collectionReference
                .document(uid)
                .update("email", email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Email update successful");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Email update failed "+ e.toString());
                    }
                });
    }

    public void setPhone(String phone) {
        collectionReference
                .document(uid)
                .update("phone", phone)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Phone update successful");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Phone update failed "+ e.toString());
                    }
                });
    }

    public void setUsername(String username) {
        collectionReference
                .document(uid)
                .update("username", username)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Username update successful");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Username update failed "+ e.toString());
                    }
                });
    }

    public void setFirstName(String firstName) {
        collectionReference
                .document(uid)
                .update("first", firstName)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "First name update successful");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "First name update failed "+ e.toString());
                    }
                });
    }

    public void setLastName(String lastName) {
        collectionReference
                .document(uid)
                .update("last", lastName)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Last name update successful");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Last name update failed "+ e.toString());
                    }
                });
    }

    //public int getBalance() {
    //    return balance;
    //}

}
