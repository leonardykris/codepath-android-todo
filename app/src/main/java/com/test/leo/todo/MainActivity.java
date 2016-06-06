package com.test.leo.todo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
  ArrayList<String> items;
  ArrayAdapter<String> itemsAdapter;
  ListView lvItems;
  EditText etNewItem;

  private final int EDIT_ITEM = 20; // Edit Item

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    lvItems = (ListView)findViewById(R.id.lvItems);
    etNewItem = (EditText)findViewById(R.id.etNewItem);

    // Read items from file
    readItems();

    // Declare an adapter for items
    itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);

    // Set the item adapter to list view items
    lvItems.setAdapter(itemsAdapter);

    // Invoke list view listener
    setupListViewListener();

    etNewItem.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange (View v, boolean hasFocus) {
        if (!hasFocus) hideSoftKeyboard(v);
      }
    });
  }

  public void onAddItem(View v) {
    // Purpose: Add item to the ListView
    // Trigger: btnAddItem:onClick
    // Params: etNewItem:value

    String itemText = etNewItem.getText().toString();

    // Add supplied text to item adapter
    itemsAdapter.add(itemText);

    // Reset the text field
    etNewItem.setText("");

    // Write items to file when new item is added
    writeItems();
  }

  private void setupListViewListener() {
    lvItems.setOnItemLongClickListener(
      new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapter, View item, int index, long id) {
          // Remove item upon long click by locating its index position
          items.remove(index);

          // Trigger adapter to sync back the items since it has been changed
          itemsAdapter.notifyDataSetChanged();

          // Write items to file when an item is deleted
          writeItems();

          return true;
        }
      }
    );

    lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapter, View item, int index, long id) {
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        i.putExtra("content", itemsAdapter.getItem(index));
        i.putExtra("index", index);

        startActivityForResult(i, EDIT_ITEM);
      }
    });
  }

  private void writeItems() {
    File filesDir = getFilesDir();
    File todoFile = new File(filesDir, "todo.txt");

    try {
      // Need to add dependency for this in build.gradle
      //  compile 'commons-io:commons-io:2.4'
      FileUtils.writeLines(todoFile, items);
    } catch (IOException error) {
      error.printStackTrace();
    }
  }

  private void readItems() {
    File filesDir = getFilesDir();
    File todoFile = new File(filesDir, "todo.txt");

    try {
      items = new ArrayList<String>(FileUtils.readLines(todoFile));
    } catch (IOException error) {
      items = new ArrayList<String>();
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == RESULT_OK && requestCode == EDIT_ITEM) {
      // Assign values
      String content = data.getExtras().getString("content");
      int index = data.getExtras().getInt("index");

      // getExtras() can return null, getIntExtra force you to assign default value if null

      // Check
      if (content != "" && content != null) {
        items.set(index, content);
        itemsAdapter.notifyDataSetChanged();

        // Write to file
        writeItems();

        // Toast
        Toast.makeText(this, content + " is edited", Toast.LENGTH_SHORT).show();
      }
    }
  }

  public void hideSoftKeyboard(View view){
    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }
}