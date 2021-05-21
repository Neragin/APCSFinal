package com.example.fulkscord.homeScreen;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fulkscord.R;
import com.example.fulkscord.directMessage.DirectMessageActivity;

import java.util.ArrayList;

/**
 * The Adapter class for the RecyclerView in HomeScreenActivity.
 *
 * @author Niranjan Mathirajan
 */
public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {
	private final ArrayList<String> localDataSet;
	private final String user;
	private final Context context;

	/**
	 * Instantiates a new Adapter.
	 *
	 * @param ctx  the context
	 * @param s1   the s 1
	 * @param user the user
	 */
	public Adapter(Context ctx, ArrayList<String> s1, String user) {
		context = ctx;
		localDataSet = s1;
		this.user = user;
	}

	@NonNull
	@Override
	public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts, parent, false);
		return new viewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull viewHolder holder, int position) {
		holder.getButton().setText(localDataSet.get(position));
		holder.getButton().setOnClickListener(v -> {
			Intent intent = new Intent(v.getContext(), DirectMessageActivity.class);
			intent.putExtra("username", user);
			intent.putExtra("friend", holder.getButton().getText());
			v.getContext().startActivity(intent);
		});
	}

	@Override
	public int getItemCount() {
		return localDataSet.size();
	}

	/**
	 * The ViewHolder class that can be served in onCreateViewHolder, and is specific to the
	 * homescreen
	 *
	 * @author Niranjan Mathirajan
	 */
	public class viewHolder extends RecyclerView.ViewHolder {

		/**
		 * The My btn 1.
		 */
		private final Button myBtn1;


		/**
		 * Instantiates a new View holder.
		 *
		 * @param itemView the item view
		 */
		public viewHolder(@NonNull View itemView) {
			super(itemView);
			myBtn1 = itemView.findViewById(R.id.chat);
		}

		/**
		 * Get button button.
		 *
		 * @return the button
		 */
		public Button getButton() {
			return myBtn1;
		}
	}
}
