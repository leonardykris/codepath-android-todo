package com.test.leo.todo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
  ArrayList<String> items;
  ArrayAdapter<String> itemsAdapter;
  ListView lvItems; // a handle for listView

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Set the XML layout activity_main to this method that starts MainActivity
    setContentView(R.layout.activity_main);

    // Create a handle for the list view from activity_main layout
    lvItems = (ListView)findViewById(R.id.lvItems);

    // Since now we're going to read and write from a file, trigger the
    // method to read the items here
    readItems();

    // Declare the adapter
    // From note:
    // Adapter allows us to easily display the contents of an ArrayList
    // within a ListView
    itemsAdapter = new ArrayAdapter<>(this, android.R.layout
            .simple_list_item_1, items);

    // Set the item adapter to list view items
    lvItems.setAdapter(itemsAdapter);

    // Invoke list view listener
    setupListViewListener();
  }

  public void onAddItem(View v) {
    // This is the method to add item to the ListView
    // Trigger condition: onClick
    // Trigger id: btnAddItem
    // Params: etNewItem value

    // Create a handle for the text field from activity_main layout
    EditText etNewItem = (EditText)findViewById(R.id.etNewItem);

    // Get the text from the text field, make sure it's in String format
    String itemText = etNewItem.getText().toString();

    // Add supplied text to item adapter
    itemsAdapter.add(itemText);

    // Reset the text field
    etNewItem.setText("");

    // Write items to file when new item is added
    writeItems();
  }

  private void setupListViewListener() {
    // Set the action to be taken when list view item receives long click
    lvItems.setOnItemLongClickListener(
      // AdapterView is a view whose children are determined by an Adapter
      // ListView is a subclass of AdapterView
      // In this case, it's lvItems with its itemsAdapter
      new AdapterView.OnItemLongClickListener() {
        @Override
        // <?> stands for generic type
        public boolean onItemLongClick(AdapterView<?> adapter, View
                item, int index, long id) {
          // Remove item upon long click by locating out its index position
          items.remove(index);

          // Trigger adapter to sync back the items since it has been changed
          itemsAdapter.notifyDataSetChanged();

          // It's to be noted that if items are added directly to the source,
          // like items instead of adding it through the adapter, the adapter
          // has to be refreshed back to reflect the changes made

          // Write items to file when an item is deleted
          writeItems();

          // End function
          return true;
        }
      }
    );
  }

  private void writeItems() {
    // Get app directory
    File filesDir = getFilesDir();

    // Create a handle to a file called todo.txt in the directory
    File todoFile = new File(filesDir, "todo.txt");

    try {
      // Need to add dependency for this in build.gradle
      //  compile 'commons-io:commons-io:2.4'
      // Write content of items to separate lines in the todo.txt
      FileUtils.writeLines(todoFile, items);
    } catch (IOException error) {
      error.printStackTrace();
    }
  }

  private void readItems() {
    // Get app directory
    File filesDir = getFilesDir();

    // Create a handle to a file called todo.txt in the directory
    File todoFile = new File(filesDir, "todo.txt");

    try {
      // Create an ArrayList from the content of the file read per line
      // Assign the ArrayList to the global items variable
      items = new ArrayList<String>(FileUtils.readLines(todoFile));
    } catch (IOException error) {
      // In case of error, like the file is empty or could not be read
      // then assign an empty ArrayList instead to items
      items = new ArrayList<String>();
    }
  }
}