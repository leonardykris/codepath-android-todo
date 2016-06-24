package com.test.leo.todo;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
  ArrayList<Task> tasks;
  TasksAdapter tasksAdapter;
  ListView items;
  Task new_task = new Task();

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
        RelativeLayout side_icon = (RelativeLayout)item.findViewById(R.id.side_icon);

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
    boolean wrapInScrollView = true;

    MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
            .title("Add Task")
            .autoDismiss(false)
            .customView(R.layout.dialog_add_item, wrapInScrollView)
            .positiveText("Add")
            .neutralText("More Options")
            .onPositive(new MaterialDialog.SingleButtonCallback() {
              @Override
              public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                View dialog_view = dialog.getCustomView();
                EditText content = (EditText)dialog_view.findViewById(R.id.content);

                new_task.content = content.getText().toString();
                new_task.save();

                // Empty new_task
                new_task = new Task();

                tasksAdapter.refreshData();

                dialog.dismiss();
              }
            })
            .onNeutral(new MaterialDialog.SingleButtonCallback() {
              @Override
              public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                View dialog_view = dialog.getCustomView();
                RelativeLayout extra = (RelativeLayout)dialog_view.findViewById(R.id.extra);

                if (extra.getVisibility() == View.VISIBLE) {
                  extra.setVisibility(View.GONE);
                  dialog.setActionButton(DialogAction.NEUTRAL, "More Options");
                } else {
                  extra.setVisibility(View.VISIBLE);
                  dialog.setActionButton(DialogAction.NEUTRAL, "Less Options");
                }
              }
            });

    MaterialDialog dialog = builder.build();
    View dialog_view = dialog.getCustomView();
    dialog.show();

    EditText content = (EditText)dialog_view.findViewById(R.id.content);
    CheckBox important = (CheckBox)dialog_view.findViewById(R.id.important);
    CheckBox due = (CheckBox)dialog_view.findViewById(R.id.due);
    RelativeLayout extra = (RelativeLayout)dialog_view.findViewById(R.id.extra);

    extra.setVisibility(View.GONE);

    content.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange (View v, boolean hasFocus) {
        if (!hasFocus) hideSoftKeyboard(v);
      }
    });
    important.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(((CheckBox)v).isChecked()){
          new_task.priority = 1;
        } else {
          new_task.priority = 0;
        }
      }
    });
    due.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View v) {
        if(((CheckBox)v).isChecked()){
          DialogFragment setDate = new DatePickerFragment();
          setDate.show(getFragmentManager(), "datePicker");
        } else {

        }
      }
    });
  }

  public void onDateSet(DatePicker view, int year, int month, int day) {
    // Do something with the date chosen by the user
    System.out.println(year + month + day);
    Log.w("DatePicker", "Date = " + year);

    Calendar c = Calendar.getInstance();
    c.set(year, month - 1, day, 0, 0);
    new_task.due = c.getTimeInMillis();

    // Attach the date returned from the datepicker fragment here
    // Like, new_task.due_date = new Date(year, month, date)
  }

  // Hide Soft Keyboard
  public void hideSoftKeyboard(View view){
    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }

}