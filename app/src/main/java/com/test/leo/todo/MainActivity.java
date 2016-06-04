package com.test.leo.todo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

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

    // Declare a list of items
    items = new ArrayList<>();

    // Declare the adapter
    // From note:
    // Adapter allows us to easily display the contents of an ArrayList
    // within a ListView
    itemsAdapter = new ArrayAdapter<>(this, android.R.layout
            .simple_list_item_1, items);

    // Test: add default items to list
    items.add("First Item");
    items.add("Second Item");

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
    items.add(itemText);

    // Reset the text field
    etNewItem.setText("");
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

          // End function
          return true;
        }
      }
    );
  }
}