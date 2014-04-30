package com.example.todolist;


import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {
	List<String> itemsList;
	ArrayAdapter<String> adapter;
	ListView list;
	String item2edit="item2edit";
	private final int successCode=1; 
	private static int editPosition;
	
	private void readItems() throws IOException{
		File fileDir=getFilesDir();
		Log.d("Main Activity FileDir",fileDir.getName());
		File file=new File(fileDir,"todo.txt");
		itemsList=new ArrayList<String>(FileUtils.readLines(file));
		
	}
	
	private void saveItems() throws IOException{
		File fileDir=getFilesDir();
		Log.d("Main Activity FileDir",fileDir.getName());
		File file=new File(fileDir,"todo.txt");
		FileUtils.writeLines(file,itemsList);
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("MainActivity","Entered on Create");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		itemsList = new ArrayList<>();
		try {
			readItems();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//itemsList=new ArrayList<>();
		adapter=new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,itemsList);
		list=(ListView) findViewById(R.id.listView1);
		list.setAdapter(adapter);
		setupLongClickListener();	
		setupOnClickListener();
	}
	
	private void setupLongClickListener() {
		list.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long rowId) {
				itemsList.remove(position);
				adapter.notifyDataSetChanged();
				try {
					saveItems();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			}
			
			
		});
	}
	
	private void setupOnClickListener() {
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long rowId) {
				// TODO Auto-generated method stub
				String editData=itemsList.get(position);
				if(editData!=null){
					editPosition=position;
					Intent navigationIntent=new Intent(MainActivity.this,EditItemActivity.class);
					navigationIntent.putExtra(item2edit, editData);
					startActivityForResult(navigationIntent, successCode);
				}
				
				
			}
			
			
			
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // Check which request we're responding to
	    if (requestCode == successCode) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {
	            itemsList.set(editPosition, data.getStringExtra(item2edit));
	            adapter.notifyDataSetChanged();
	            try {
					saveItems();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void Add(View view) throws IOException {
		EditText newItem=(EditText)findViewById(R.id.editText1);
		if(newItem.getText().length()!=0){
			Log.d("MainACtivity Add",newItem.getText().toString()+"is not null");
			itemsList.add(newItem.getText().toString());
			adapter.notifyDataSetChanged();
			newItem.setText("");
			saveItems();
		}else{
			Log.d("MainACtivity Add",newItem.getText().toString()+"is null");
			Context context = getApplicationContext();
			CharSequence text = "Cannot add empty item in the list";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
	}

}
