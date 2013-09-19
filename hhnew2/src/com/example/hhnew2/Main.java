package com.example.hhnew2;

import java.util.ArrayList;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class Main extends ListActivity {

	private ArrayList<Pars> messages;
	private Adapterlist adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			messages = savedInstanceState.getParcelableArrayList("messages");
			adapter = new Adapterlist(Main.this, R.layout.temp_listview,
					messages);
			setListAdapter(adapter);
		} else
			new GetParserResult().execute();
	}
@Override
public boolean onCreateOptionsMenu(Menu menu) {
	menu.add(1, 1, 1, R.string.refresh);
	menu.add(1, 2, 2, R.string.exit);
	return true;};
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case 1:
			new GetParserResult().execute();
			break;
		case 2:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	};
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent k=new Intent(Intent.ACTION_VIEW,Uri.parse(messages.get(position).link));
		startActivity(k);
	}

	protected void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putParcelableArrayList("messages", messages);
	}

	private class GetParserResult extends
			AsyncTask<Context, Integer, ArrayList<Pars>> {
		private ProgressDialog loadingDialog;
		private NewParser parser;

		protected void onPreExecute() {
			parser = new NewParser();
			loadingDialog = ProgressDialog.show(Main.this, "",
					"Please wait...", true);
		}

		@Override
		protected ArrayList<Pars> doInBackground(Context... arg0) {
			return parser.parse();
		}

		protected void onPostExecute(ArrayList<Pars> result) {
			if (result == null) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						Main.this);
				builder.setMessage("Error loading http://lenta.ru/rss/last24 feed")
						.setCancelable(false)
						.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
										loadingDialog.dismiss();
										cancel(true);
									}
								});
				AlertDialog alert = builder.create();
				alert.show();
			} else {
				messages = result;
				loadingDialog.dismiss();
				adapter = new Adapterlist(Main.this,
						R.layout.temp_listview, messages);
				setListAdapter(adapter);
			}
		}
	}
}
