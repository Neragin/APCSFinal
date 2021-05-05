package com.example.fulkscord.homeScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fulkscord.DatabaseKeys;
import com.example.fulkscord.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

/**
 * The type Home screen activity.
 */
public class HomeScreenActivity extends AppCompatActivity {

	int friendChildren;
	boolean firstRead = true;

	private RecyclerView recyclerView;
	private ArrayList<String> s1;
	private String user;
	private DatabaseReference mDatabase;
	private EditText newFriend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);

		mDatabase = FirebaseDatabase.getInstance().getReference();
		s1 = new ArrayList<String>();
		newFriend = (EditText) findViewById(R.id.friends);

		newFriend.setOnKeyListener((v, keyCode, event) -> {
			if(event.getAction() == KeyEvent.ACTION_DOWN && (keyCode == KeyEvent.KEYCODE_ENTER)) addFriend(); //Checks if it is pressed and is entered
			return false;
		});

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		user = bundle.getString("username");

		//s1 = new String[]{"test", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2" , "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2" , "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2" , "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2"};

		recyclerView = findViewById(R.id.recyclerview);

		Adapter adapter = new Adapter(this, s1, user);


		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		mDatabase.child(DatabaseKeys.userKey).child(user).child(DatabaseKeys.friendsKey).addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {

				if(firstRead) {
					friendChildren = (int) snapshot.getChildrenCount();
					firstRead = false;
				}

				if(snapshot.getChildrenCount() < friendChildren){
					s1.clear();
					for(DataSnapshot ds : snapshot.getChildren()){
						s1.add(ds.getValue().toString());
					}
					friendChildren = (int) snapshot.getChildrenCount();
					adapter.notifyDataSetChanged();
				}

				for (DataSnapshot ds : snapshot.getChildren()) {
					if (!s1.contains(ds.getValue().toString())) s1.add(ds.getValue().toString());
				}
				adapter.notifyItemInserted(s1.size() - 1);
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


	}

	private void addFriend() {
		String name = newFriend.getText().toString().trim();

		mDatabase.child(DatabaseKeys.userKey).addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				for(DataSnapshot ds : snapshot.getChildren()){
					if(ds.child("username").getValue().toString().equals(name)){
						mDatabase.child(DatabaseKeys.userKey).child(name).child(DatabaseKeys.friendsKey).child(user).setValue(user);
						mDatabase.child(DatabaseKeys.userKey).child(user).child(DatabaseKeys.friendsKey).child(name).setValue(name);
						return;
					}
				}
				Toast.makeText(HomeScreenActivity.this, "failed to make friend", Toast.LENGTH_SHORT).show();

			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});
	}
}
