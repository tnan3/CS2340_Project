package cs2340.gatech.edu.m4.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.Random;

import cs2340.gatech.edu.m4.R;
import cs2340.gatech.edu.m4.model.DataDatabaseHelper;
import cs2340.gatech.edu.m4.model.DataItem;
import cs2340.gatech.edu.m4.model.SimpleModel;

public class ReportActivity extends AppCompatActivity {

    private EditText dateText;
    private EditText locationText;
    private EditText zipText;
    private EditText addressText;
    private EditText cityText;
    private EditText boroughText;
    private EditText latitudeText;
    private EditText longitudeText;
    private Random random;
    private DataDatabaseHelper dataDatabaseHelper;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        dateText = (EditText) findViewById(R.id.date);
        locationText = (EditText) findViewById(R.id.location);
        zipText = (EditText) findViewById(R.id.zip);
        addressText = (EditText) findViewById(R.id.address);
        cityText = (EditText) findViewById(R.id.city);
        boroughText = (EditText) findViewById(R.id.borough);
        latitudeText = (EditText) findViewById(R.id.latitude);
        longitudeText = (EditText) findViewById(R.id.longitude);
        random = new Random();
        dataDatabaseHelper = new DataDatabaseHelper(this, "Data.db", null, 1);
        db = dataDatabaseHelper.getWritableDatabase();


        Button report_cancelButton = (Button) findViewById(R.id.report_cancel_button);
        report_cancelButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent report_cancelIntent = new Intent(ReportActivity.this, MainActivity.class);
                ReportActivity.this.startActivity(report_cancelIntent);
                finish();
            }
        });

        Button report_writeButton = (Button) findViewById(R.id.report_write_button);
        report_writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WriteData();
                Intent report_writeIntent = new Intent(ReportActivity.this, MainActivity.class);
                ReportActivity.this.startActivity(report_writeIntent);
                finish();
            }
        });
    }

    private void WriteData(){
        SimpleModel model = SimpleModel.INSTANCE;
        int genId = 10000000 + random.nextInt(90000000);
        while (model.containsId(genId)){
            genId = 10000000 + random.nextInt(90000000);
        }
        Log.d("ReportActivity_genId", genId + "");
        DataItem data = new DataItem(genId,dateText.getText().toString(), locationText.getText().toString(), Integer.valueOf(zipText.getText().toString()), addressText.getText().toString(), cityText.getText().toString(), boroughText.getText().toString(), Float.valueOf(latitudeText.getText().toString()), Float.valueOf(longitudeText.getText().toString()));
        model.addId(genId);
        DataDatabaseHelper.readIntoDatabase(db, data);
    }

}
