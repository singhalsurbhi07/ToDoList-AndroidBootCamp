package com.example.todolist;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.os.Build;

public class EditItemActivity extends Activity {
	EditText editText;
	String item2edit="item2edit";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		editText=(EditText) findViewById(R.id.editText1);
		editText.setText(getIntent().getStringExtra(item2edit));
		
	}
	
	public void save(View view){
		Intent returnIntent = new Intent();
		EditText newEditText=(EditText) findViewById(R.id.editText1);
		String newItem=newEditText.getText().toString();
		 returnIntent.putExtra(item2edit,newItem);
		 setResult(RESULT_OK,returnIntent);     
		 finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	

}
