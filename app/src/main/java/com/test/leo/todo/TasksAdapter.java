package com.test.leo.todo;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 19/06/16.
 */
public class TasksAdapter extends ArrayAdapter<Task> {
  private final Context context;
  private ArrayList<Task> tasks;

  public TasksAdapter(Context context, ArrayList<Task> tasks) {
    super(context, 0, tasks);
    this.context = context;
    this.tasks = tasks;
  }

  public void refreshData() {
    List<Task> tasks = Task.findWithQuery(Task.class, "Select * from Task order by priority desc");
    this.tasks.clear();
    this.tasks.addAll(tasks);
    notifyDataSetChanged();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_item, parent, false);
    }

    TextView content = (TextView)convertView.findViewById(R.id.content);

    final int p = position;
    final Task task = getItem(position);
    content.setText(task.content);

    final ImageView check = (ImageView)convertView.findViewById(R.id.check);
    final ImageView edit = (ImageView)convertView.findViewById(R.id.edit);
    final ImageView notification = (ImageView)convertView.findViewById(R.id.notification);
    final ImageView priority = (ImageView)convertView.findViewById(R.id.priority);
    final ImageView priority_icon = (ImageView)convertView.findViewById(R.id.priority_icon);

    check.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View view) {
        task.isCompleted = true;
        task.save();
        Snackbar.make(view, "Check clicked", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
      }
    });

    edit.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Edit clicked", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
      }
    });
    notification.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Notification clicked", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
      }
    });
    priority.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View view) {
        if (task.priority > 0) {
          task.priority = 0;
        } else {
          task.priority = 1;
        }
        task.save();

        refreshData();

        Snackbar.make(view, "Priority clicked", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
      }
    });

    if (task.priority > 0) {
      priority_icon.setVisibility(View.VISIBLE);
      priority.setColorFilter(Color.parseColor("#42a5f5"));
    } else {
      priority_icon.setVisibility(View.INVISIBLE);
      priority.setColorFilter(Color.parseColor("#999999"));
    }

    RelativeLayout menu = (RelativeLayout)convertView.findViewById(R.id.item_menu);
    menu.setVisibility(View.GONE);

    return convertView;
  }
}
