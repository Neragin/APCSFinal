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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

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

import java.util.ArrayList;
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

	/**
	 * The Dm adapter.
	 */
	DMAdapter dmAdapter;
	/**
	 * The Messages.
	 */
	ArrayList<Message> messages;

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

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

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
		messages = new ArrayList<>();
//		getAllMessages();
		dmAdapter = new DMAdapter(this, messages);
		recyclerView.setAdapter(dmAdapter);
		LinearLayoutManager fulk = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(fulk);
	}

	/**
	 * Respond to enter.
	 */
	public void respondToEnter(){
		/**
		 * Should respond to the Enter Keyword
		 */
		String message = sendMessage.getText().toString().trim();
		sendMessage(message);
		sendMessage.setText("");
//		dmAdapter.notifyDataSetChanged();
//		System.out.println("LIST IS: " + messages.toString());
		getAllMessages();
		((ScrollView) findViewById(R.id.fulk)).fullScroll(ScrollView.FOCUS_DOWN);

	}

	/**
	 * Send message.
	 *
	 * @param text the text
	 */
	public void sendMessage(String text){
		Message msg = new Message(text, "000000", user, friend, new Date());
		mDatabase.child(DatabaseKeys.dmKey).child(Integer.toString(user.hashCode() + friend.hashCode())).child(new Date().toString()).setValue(msg);
	}

	/**
	 * Get all messages.
	 */
//TODO(NEED TO FIX THIS)
	public void getAllMessages(){
		LinkedList<Message> lst = new LinkedList<>();



		mDatabase.child(DatabaseKeys.dmKey).child(Integer.toString(user.hashCode() + friend.hashCode())).addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				messages.clear();
				for(DataSnapshot ds : snapshot.getChildren()){
//					System.out.println(ds);
					Message msg = new Message(ds.child("text").getValue().toString(), ds.child("key").getValue().toString(), ds.child("sender").getValue().toString(), "me?", new Date()); //need to actually do smthng w/ me?
					messages.add(msg);
					System.out.println(msg.toString());
				}
				dmAdapter.notifyDataSetChanged();


			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});
	}
}