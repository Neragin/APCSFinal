package com.example.fulkscord.directMessage;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.fulkscord.DatabaseKeys;
import com.example.fulkscord.R;
import com.example.fulkscord.homeScreen.Adapter;
import com.example.fulkscord.messages.Message;
import com.example.fulkscord.user.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.LinkedList;
import java.util.Stack;

/**
 * The type Direct message activity.
 */
public class DirectMessageActivity extends AppCompatActivity {

	private String user, friend;
	private DatabaseReference mDatabase;
	private RecyclerView recyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_direct_message);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		user = bundle.getString("username");
		friend = bundle.getString("friend");

		mDatabase = FirebaseDatabase.getInstance().getReference();
		recyclerView = findViewById(R.id.messages);
		/**
		 * 1. Plain text at bottom to send messages
		 * 2. Recycler View to display
		 * 3. POST and GET from firebase
		 */
		DMAdapter dmAdapter = new DMAdapter(this, new LinkedList<Message>());
		sendMessage("Hello world");
		recyclerView.setAdapter(dmAdapter);
	}

	public void respondToEnter(){
		/**
		 * Should respond to the Enter Keyword
		 */
	}

	public void sendMessage(String text){
		Message msg = new Message(text, "000000", user, friend, new Date());
		mDatabase.child(DatabaseKeys.dmKey).child(Integer.toString((user + friend).hashCode())).child(new Date().toString()).setValue(msg);
	}

	public void getAllMessages(){
		LinkedList<Message> lst = new LinkedList<Message>();

		mDatabase.child(DatabaseKeys.dmKey).child(Integer.toString((user + friend).hashCode())).addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				for(DataSnapshot ds : snapshot.getChildren()){

				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});
	}
}