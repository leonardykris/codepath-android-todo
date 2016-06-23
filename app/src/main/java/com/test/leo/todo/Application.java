package com.test.leo.todo;

import com.orm.SugarApp;

/**
 * Created by leo on 23/06/16.
 */
public class Application extends SugarApp {
  @Override
  public void onCreate() {
    super.onCreate();

    Task.findById(Task.class, (long) 1);
  }
}
