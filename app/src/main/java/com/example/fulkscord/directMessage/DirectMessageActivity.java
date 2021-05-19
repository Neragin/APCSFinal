package com.example.fulkscord.directMessage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fulkscord.DatabaseKeys;
import com.example.fulkscord.R;
import com.example.fulkscord.messages.Message;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

/**
 * The type Direct message activity.
 *
 * @author Kaustubh
 */
public class DirectMessageActivity extends AppCompatActivity {

	private static final int REQUEST_CALL = 1;
	/**
	 * The Dm adapter.
	 */
	DMAdapter dmAdapter;
	/**
	 * The Messages.
	 */
	ArrayList<Message> messages;
	private String user, friend;
	private DatabaseReference mDatabase;
	private RecyclerView recyclerView;
	private EditText sendMessage;
	private String otherPhNum = "tel:";
	private final TextView.OnEditorActionListener listener = (TextView textView, int i, KeyEvent keyEvent) -> {
		if (i == EditorInfo.IME_ACTION_SEND) {
			respondToEnter();
		}
		return false;
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_direct_message);

		sendMessage = findViewById(R.id.sendMessage);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		user = bundle.getString("username");
		friend = bundle.getString("friend");

		mDatabase = FirebaseDatabase.getInstance().getReference();
		recyclerView = findViewById(R.id.messages);

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

//		sendMessage.setOnEditorActionListener(listener);


		sendMessage.setRawInputType(InputType.TYPE_CLASS_TEXT);
		sendMessage.setImeOptions(EditorInfo.IME_ACTION_GO);

		TextView.OnEditorActionListener EnterOnText = new TextView.OnEditorActionListener() {
			public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_GO) {
					final String msg = view.getText().toString().trim();
					if (!msg.isEmpty()) {
						// Do whatever you need here
						respondToEnter();
					}
				}
				return true;
			}
		};
		sendMessage.setOnEditorActionListener(EnterOnText);
		sendMessage.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN && (keyCode == KeyEvent.KEYCODE_ENTER))
					respondToEnter(); //Checks if it is pressed and is entered
				return false;
			}
		});

		//Updates user's phone number
		mDatabase.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				for (Map.Entry<String, Object> user : ((Map<String, Object>) snapshot.getValue()).entrySet()) {
					Map f = (Map) user.getValue();
					if (f.get("username").toString().trim().equals(friend)) {
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

		findViewById(R.id.fulkPhone).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				fulkCall();
			}
		});
	}

	public void fulkCall() {
		if (ContextCompat.checkSelfPermission(DirectMessageActivity.this,
				Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

			ActivityCompat.requestPermissions(DirectMessageActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);

		} else {
			startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(otherPhNum)));
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		if (requestCode == REQUEST_CALL) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				fulkCall();
			} else {
				Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * Respond to enter.
	 */
	public void respondToEnter() {
		/**
		 * Should respond to the Enter Keyword
		 */
		String message = sendMessage.getText().toString().trim();
		sendMessage(message);
		sendMessage.setText("");
		getAllMessages();
	}

	/**
	 * Send message.
	 *
	 * @param text the text
	 */
	public void sendMessage(String text) {
		Message msg = new Message(text, "000000", user, friend, new Date());
		mDatabase.child(DatabaseKeys.dmKey).child(Integer.toString(user.hashCode() + friend.hashCode())).child(new Date().toString()).setValue(msg);
	}

	/**
	 * Get all messages.
	 */
//TODO(NEED TO FIX THIS)
	public void getAllMessages() {
		LinkedList<Message> lst = new LinkedList<>();


		mDatabase.child(DatabaseKeys.dmKey).child(Integer.toString(user.hashCode() + friend.hashCode())).addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				messages.clear();
				for (DataSnapshot ds : snapshot.getChildren()) {
//					System.out.println(ds);
					Message msg = new Message(ds.child("text").getValue().toString(), ds.child("key").getValue().toString(), ds.child("sender").getValue().toString(), "me?", new Date()); //need to actually do smthng w/ me?
					messages.add(msg);
					System.out.println(msg.toString());
				}
				dmAdapter.notifyDataSetChanged();

				if (recyclerView.canScrollVertically(1))
					recyclerView.smoothScrollToPosition(messages.size() - 1);


			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});

	}
}