package edu.skku.map.a2016310526_personalproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.skku.map.a2016310526_personalproj.FireBaseConn.FirebasePost;

public class Activity_login extends AppCompatActivity {


    private DatabaseReference mPostReference;
    ArrayList<String> nameInfo, passInfo;

    String name, pass, fullname, birthday, email;

    EditText e1, e2, e3, e4, e5;
    Button LoginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPostReference = FirebaseDatabase.getInstance().getReference("user_info");
        nameInfo = new ArrayList<>();
        passInfo = new ArrayList<>();
        getFirebaseDatabase();

        e1 = findViewById(R.id.editText);
        e2 = findViewById(R.id.editText2);
        e3 = findViewById(R.id.editText3);
        e4 = findViewById(R.id.editText4);
        e5 = findViewById(R.id.editText5);

        LoginBtn = findViewById(R.id.button);

        LoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                name = e1.getText().toString();
                pass = e2.getText().toString();
                fullname = e3.getText().toString();
                birthday = e4.getText().toString();
                email = e5.getText().toString();


                if(name.equals("")){
                    Toast.makeText(Activity_login.this, "Name...required...",Toast.LENGTH_SHORT).show();
                }
                else if(pass.equals("")){
                    Toast.makeText(Activity_login.this, "Password...required...",Toast.LENGTH_SHORT).show();
                }
                else if(fullname.equals("")){
                    Toast.makeText(Activity_login.this, "Fullname...required...",Toast.LENGTH_SHORT).show();
                }
                else if(birthday.equals("")){
                    Toast.makeText(Activity_login.this, "Birthday...required...",Toast.LENGTH_SHORT).show();
                }
                else if(email.equals("")){
                    Toast.makeText(Activity_login.this, "Email...required...",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(!nameInfo.contains(name)){
                        postFirebaseDatabase(true);
                        Intent intent = new Intent(Activity_login.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(Activity_login.this, "Please use another name",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void signInWithNamePassword(String _name, String _pass) {

        mPostReference = FirebaseDatabase.getInstance().getReference("user_info");
        mPostReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nameInfo.clear();
                passInfo.clear();

                for(DataSnapshot postSnapShot : dataSnapshot.getChildren()){
                    FirebasePost info = postSnapShot.getValue(FirebasePost.class);

                    nameInfo.add(info.getUsername());
                    passInfo.add(info.getPassword());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        for(int i =0; i < nameInfo.size(); i++){
            String CompName = nameInfo.get(i);
            String CompPass = passInfo.get(i);

            if(CompName.equals(_name)){
                if(CompPass.equals(_pass)){

                    postFirebaseDatabase(true);
                    Intent intent = new Intent(Activity_login.this, MainActivity.class);

                    startActivity(intent);
                    finish();
                }
            }
            else{
                Toast.makeText(Activity_login.this, "wrong password", Toast.LENGTH_LONG).show();
                break;
            }
        }
    }




    public void postFirebaseDatabase(boolean add){
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;

        if(add){
            FirebasePost post = new FirebasePost(name, fullname, pass, birthday, email );
            postValues = post.toMap();
        }
        childUpdates.put(name, postValues);
        mPostReference.updateChildren(childUpdates);
    }

    public void getFirebaseDatabase() {
        final ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!nameInfo.isEmpty()) nameInfo.clear();
                if(!passInfo.isEmpty()) passInfo.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    FirebasePost info = postSnapshot.getValue(FirebasePost.class);
                    assert info != null;
                    nameInfo.add(info.getUsername());
                    passInfo.add(info.getPassword());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mPostReference.child("user_info").addValueEventListener(postListener);
    }

}

