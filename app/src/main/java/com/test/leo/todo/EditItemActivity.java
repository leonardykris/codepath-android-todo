package com.test.leo.todo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
  EditText etEditItem;
  String content;
  int index;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_item);

    content = getIntent().getExtras().getString("content");
    index = getIntent().getExtras().getInt("index");

    // Use the content from list item to fill up the textfield
    etEditItem = (EditText)findViewById(R.id.etEditItem);
    etEditItem.append(content);

    etEditItem.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange (View v, boolean hasFocus) {
        if (!hasFocus) hideSoftKeyboard(v);
      }
    });
  }

  protected void saveItem(View v) {
    // Purpose: Save edited item
    // Trigger: btnSaveItem:onClick
    // Params: etEditItem:value

    // Finish Activity
    Intent data = new Intent();

    data.putExtra("content", etEditItem.getText().toString());
    data.putExtra("index", index);

    setResult(RESULT_OK, data); // RESULT_OK is public
    finish(); // closes activity
  }

  public void hideSoftKeyboard(View view){
    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }
}
