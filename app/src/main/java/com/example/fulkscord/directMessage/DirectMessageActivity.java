package com.example.fulkscord.directMessage;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.fulkscord.DatabaseKeys;
import com.example.fulkscord.MainActivity;
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
import java.util.Map;
import java.util.Stack;

/**
 * The type Direct message activity.
 */
public class DirectMessageActivity extends AppCompatActivity {

	private String user, friend;
	private DatabaseReference mDatabase;
	private RecyclerView recyclerView;
	private EditText sendMessage;
	private static final int REQUEST_CALL = 1;
	private String otherPhNum = "tel:";

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
		mDatabase.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				for (Map.Entry<String, Object> user : ((Map<String, Object>) snapshot.getValue()).entrySet())
				{
					Map f = (Map) user.getValue();
					if (f.get("username").toString().trim().equals(friend))
					{
						otherPhNum += f.get("phoneNumber");
					}
				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {
				System.out.println("Oops");
			}
		});


		/**
		 * 1. Plain text at bottom to send messages
		 * 2. Recycler View to display
		 * 3. POST and GET from firebase
		 */
		messages = new ArrayList<>();
		dmAdapter = new DMAdapter(this, messages);
		recyclerView.setAdapter(dmAdapter);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		linearLayoutManager.setStackFromEnd(true);
		recyclerView.setLayoutManager(linearLayoutManager);
		recyclerView.scrollToPosition(messages.size() - 1);
		getAllMessages();

		findViewById(R.id.fulkPhone).setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View view) {
				fulkCall();
			}
		});
	}

	public void fulkCall()
	{
		if (ContextCompat.checkSelfPermission(DirectMessageActivity.this,
				Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){

			ActivityCompat.requestPermissions(DirectMessageActivity.this, new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);

		} else {
			startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(otherPhNum)));
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		if (requestCode == REQUEST_CALL)
		{
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
				fulkCall();
			}
			else
			{
				Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
			}
		}
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
//		((ScrollView) findViewById(R.id.fulk)).fullScroll(ScrollView.FOCUS_DOWN);
//		recyclerView.smoothScrollToPosition(messages.size() - 1);

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

				if(recyclerView.canScrollVertically(1)) recyclerView.smoothScrollToPosition(messages.size() - 1);


			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});

	}
}