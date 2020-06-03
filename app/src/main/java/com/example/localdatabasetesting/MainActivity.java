package com.example.localdatabasetesting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.localdatabasetesting.databinding.ActivityMainBinding;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MyDatabaseHelper myDB;
    private ArrayList<String> car_id, car_name, car_model, car_mileage, car_fuel, car_engine, car_vin;
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.carMainToolbar.setTitle("Twoje auta");
        binding.carMainToolbar.setLogo(R.drawable.ic_car);
        setSupportActionBar(binding.carMainToolbar);

        binding.addCarButton.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AddCar.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_up, R.anim.slide_out_up);
                finish();

            }
        });

        myDB = new MyDatabaseHelper(MainActivity.this);
        car_id = new ArrayList<>();
        car_name = new ArrayList<>();
        car_model = new ArrayList<>();
        car_mileage = new ArrayList<>();
        car_fuel = new ArrayList<>();
        car_engine = new ArrayList<>();
        car_vin = new ArrayList<>();

        storeInArray();

        customAdapter = new CustomAdapter(MainActivity.this, car_id, car_name, car_model, car_mileage, car_fuel, car_engine, car_vin);
        binding.recyclerView.setAdapter(customAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }



    void storeInArray(){

        Cursor cursor = myDB.readAllDataFromMainTable();
        if(cursor.getCount() == 0) {

        }
        else
        {
            while(cursor.moveToNext()){
                car_id.add(cursor.getString(0));
                car_name.add(cursor.getString(1));
                car_model.add(cursor.getString(2));
                car_mileage.add(cursor.getString(3));
                car_fuel.add(cursor.getString(4));
                car_engine.add(cursor.getString(5));
                car_vin.add(cursor.getString(6));
            }
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.search:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out, R.anim.slide_out_down);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
