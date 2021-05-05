package com.example.fulkscord.directMessage;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

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
	private EditText sendMessage;

	DMAdapter dmAdapter;
	LinkedList<Message> messages;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_direct_message);

		sendMessage = (EditText) findViewById(R.id.sendMessage);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		user = bundle.getString("username");
		friend = bundle.getString("friend");

		mDatabase = FirebaseDatabase.getInstance().getReference();
		recyclerView = findViewById(R.id.messages);

		sendMessage.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(event.getAction() == KeyEvent.ACTION_DOWN && (keyCode == KeyEvent.KEYCODE_ENTER)) respondToEnter(); //Checks if it is pressed and is entered
				return false;
			}
		});


		/**
		 * 1. Plain text at bottom to send messages
		 * 2. Recycler View to display
		 * 3. POST and GET from firebase
		 */
		messages = new LinkedList<Message>();
		dmAdapter = new DMAdapter(this, messages);
		sendMessage("Hello world");
		recyclerView.setAdapter(dmAdapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
	}

	public void respondToEnter(){
		/**
		 * Should respond to the Enter Keyword
		 */
		String message = sendMessage.getText().toString().trim();
		sendMessage(message);
		sendMessage.setText("");
		System.out.println(getAllMessages().size());
//		dmAdapter.notifyDataSetChanged();
		System.out.println(messages);

	}

	public void sendMessage(String text){
		Message msg = new Message(text, "000000", user, friend, new Date());
		mDatabase.child(DatabaseKeys.dmKey).child(Integer.toString((user + friend).hashCode())).child(new Date().toString()).setValue(msg);
	}

	//TODO(NEED TO FIX THIS)
	public LinkedList<Message> getAllMessages(){
		LinkedList<Message> lst = new LinkedList<Message>();

		mDatabase.child(DatabaseKeys.dmKey).child(Integer.toString((user + friend).hashCode())).addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				System.out.println(snapshot.getChildrenCount());
				for(DataSnapshot ds : snapshot.getChildren()){
//					System.out.println(ds);
					Message msg = new Message(ds.child("text").getValue().toString(), ds.child("key").getValue().toString(), ds.child("sender").getValue().toString(), "me?", new Date()); //need to actually do smthng w/ me?
					if(!messages.contains(msg)) messages.add(msg);
//					System.out.println(msg.toString());
//					lst.add((Message) ds.getValue()); //may break the code
//					System.out.println("here");
				}
//				dmAdapter.notifyDataSetChanged();
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});
		while(lst == null) {
			//yeah i know, sketchy
		}
		return lst;
	}
}