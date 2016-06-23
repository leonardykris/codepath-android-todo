package com.test.leo.todo;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

public class Task extends SugarRecord {
  @Unique
  public String content;
  public boolean isCompleted;
  public boolean notify;
  public int priority;


  // Default constructor
  public Task() {

  }

  public Task(String content) {
    this.content = content;
    this.isCompleted = false;
    this.priority = 0;
    this.notify = false;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
