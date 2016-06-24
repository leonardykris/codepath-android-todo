package com.test.leo.todo;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.util.Calendar;

public class Task extends SugarRecord {
  @Unique
  public String content;
  public int priority;
  public boolean isCompleted;
  public boolean notify;
  public boolean hasReminder;

  // Dates
  public long due;
  public long reminder_date;
  public long created_at;
  public long updated_at;


  // Default constructor
  public Task() {

  }

  public Task(String content) {
    this.content = content;
    this.isCompleted = false;
    this.priority = 0;
    this.notify = false;
    this.due = 0;
    this.reminder_date = 0;

    Calendar now = Calendar.getInstance();
    this.created_at = now.getTimeInMillis();
    this.updated_at = this.created_at;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
