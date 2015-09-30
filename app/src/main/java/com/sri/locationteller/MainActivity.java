package com.sri.locationteller;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    String[] interval={"10","20","30","40","50","60"};
    EditText mEdit;
    Spinner mySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayAdapter<String> intervalAdapter = new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item, interval);
        Spinner intervalSpinner= (Spinner) findViewById(R.id.spinner);
        intervalSpinner.setAdapter(intervalAdapter);
        mEdit   = (EditText)findViewById(R.id.editText);
        mySpinner=(Spinner) findViewById(R.id.spinner);
    }

    public void startLocService(View view)
    {
        if (mEdit.getText().toString().length()>=10)
        {
            Intent intent=new Intent(this,MapActivity.class);
            intent.putExtra("phNo",mEdit.getText().toString());
            intent.putExtra("interval",mySpinner.getSelectedItem().toString());
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this.getApplicationContext(),"Please Enter a valid Mobile Number",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {
            startActivity(new Intent(MainActivity.this,AboutActivity.class));
           // return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
