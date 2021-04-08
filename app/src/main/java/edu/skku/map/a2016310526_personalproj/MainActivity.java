package edu.skku.map.a2016310526_personalproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.skku.map.a2016310526_personalproj.FireBaseConn.FirebasePost;

public class MainActivity extends AppCompatActivity {
    EditText eduser, edpass;
    Button btnLogin;
    TextView textSignup;
    String name, pass;

    private List<Slide> listslides;
    private ViewPager sliderpager;

    ArrayList<String> nameInfo, passInfo;


    private DatabaseReference mPostReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPostReference = FirebaseDatabase.getInstance().getReference();
        nameInfo = new ArrayList<>();
        passInfo = new ArrayList<>();
        getFirebaseDatabase();

        listslides = new ArrayList<>();

        listslides.add(new Slide(R.drawable.month, "8월의 크리스마스 / Do you want to get more Info?"));

        listslides.add(new Slide(R.drawable.notebook, "NoteBook / Do you want to get more Info?"));
        listslides.add(new Slide(R.drawable.avengers, "Avengers / Do you want to get more Info?"));

        listslides.add(new Slide(R.drawable.snowpiercer, "SnowPiercer / Do you want to get more Info?"));

        sliderpager = findViewById(R.id.slide_pager);

        SliderPagerAdapter adapter = new SliderPagerAdapter(this, listslides);

        sliderpager.setAdapter(adapter);


        eduser = findViewById(R.id.input_username);
        edpass = findViewById(R.id.input_password);

        textSignup =  findViewById(R.id.signup_text);
        btnLogin = findViewById(R.id.login_button);


        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                name = eduser.getText().toString();
                pass = edpass.getText().toString();
                int cnt =0;

                if(name.equals("") || pass.equals("")){
                    Toast.makeText(MainActivity.this, "please fill all the blank", Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    if(!nameInfo.contains(name)){
                        Toast.makeText(MainActivity.this, "wrong username", Toast.LENGTH_LONG).show();
                    }
                    else{
                        for(String CompName :nameInfo){
                            if(CompName.equals(name)) break;
                            cnt++;
                        }

                        if(!passInfo.get(cnt).equals(pass)){
                            Toast.makeText(MainActivity.this, "wrong password", Toast.LENGTH_LONG).show();
                        }


                        else{

                            Intent j = new Intent(MainActivity.this, Second_Activity.class);
                            startActivity(j);

                        }
                    }
                }


            }
        });
        textSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, Activity_login.class);
                startActivity(i);
            }
        });
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
