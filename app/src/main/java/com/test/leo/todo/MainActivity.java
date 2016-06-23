package com.test.leo.todo;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  ArrayList<Task> tasks;
  TasksAdapter tasksAdapter;
  ListView items;
  EditText newItem;

  private final int EDIT_ITEM = 20; // Edit Item

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

//    populateDB();
    tasks = retrieveTasks();

    items = (ListView)findViewById(R.id.items);
    tasksAdapter = new TasksAdapter(this, tasks);
    items.setAdapter(tasksAdapter);


    items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapter, View item, int index, long id) {
        Snackbar.make(item, "List View item clicked", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();

        RelativeLayout menu = (RelativeLayout)item.findViewById(R.id.item_menu);

        if (menu.getVisibility() == View.VISIBLE){
          menu.setVisibility(View.GONE);
        } else {
          menu.setVisibility(View.VISIBLE);
        }
      }
    });
  }

  protected void populateDB() {

//    Task first = new Task("Subscribe to Crunchyroll");
//    Task second = new Task("Watch Kiznaiver's last episode");
//    Task third = new Task("Fly to SF");
//
//    first.save();
//    second.save();
//    third.save();

//    Task item = new Task("My most important treasure");
//    item.priority = 1;
//    item.save();
  }

  protected ArrayList<Task> retrieveTasks() {
//    List<Task> tasks = Task.listAll(Task.class);
    List<Task> tasks = Task.findWithQuery(Task.class, "Select * from Task order by priority desc");

    return new ArrayList<Task>(tasks);
  }

  public void showAddItemDialog(View view) {

    MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
            .title("Add Item")
            .content("Well this is supposed to be a dialog fragment")
            .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)
            .input("Content goes here", "Prefilled text", new MaterialDialog.InputCallback() {
              @Override
              public void onInput(MaterialDialog dialog, CharSequence input) {
                // Do something
                System.out.println("=============Clicked");
              }
            });
    MaterialDialog dialog = builder.build();
    dialog.show();
  }
}