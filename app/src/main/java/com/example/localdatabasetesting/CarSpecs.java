package com.example.localdatabasetesting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.localdatabasetesting.databinding.ActivityCarSpecsBinding;

import java.util.ArrayList;

public class CarSpecs extends AppCompatActivity {

    private ActivityCarSpecsBinding binding;
    private String carid, carname, carmodel, carfuel, carengine, carmileage, carvin;
    private MyDatabaseHelper myDB;
    private ArrayList<String> column_id, curr_car_id, car_sequence, car_changes, curr_date;
    private CarSpecsAdapter carSpecsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCarSpecsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        carid = (getIntent().getStringExtra("ID"));
        carname = (getIntent().getStringExtra("NAME"));
        carmodel = (getIntent().getStringExtra("MODEL"));
        carmileage = (getIntent().getStringExtra("MILEAGE"));
        carfuel = (getIntent().getStringExtra("FUEL"));
        carengine = (getIntent().getStringExtra("ENGINE"));
        carvin = (getIntent().getStringExtra("VIN"));

        binding.carSpecsToolbar.setLogo(R.drawable.ic_car);
        binding.carSpecsToolbar.setTitle(carname);
        setSupportActionBar(binding.carSpecsToolbar);

     //   binding.carNameSPECS.setText(carname);
        binding.carModelSPECS.setText(carmodel);
        binding.carFuelSPECS.setText("Paliwo: "+carfuel);
        binding.carEngineSPECS.setText(carengine);
        binding.carMileageSPECS.setText(spaceBetweenMileageNumbers(carmileage)+"km");
        binding.carVinSPECS.setText("VIN: "+carvin);

        binding.addNewInfoToCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CarSpecs.this, AddNewCarInfo.class);
                intent.putExtra("CARID", carid);
                intent.putExtra("NAME", carname);
                intent.putExtra("MODEL", carmodel);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }
        });

        myDB = new MyDatabaseHelper(CarSpecs.this);
        column_id = new ArrayList<>();
        curr_car_id = new ArrayList<>();
        car_sequence = new ArrayList<>();
        car_changes = new ArrayList<>();
        curr_date = new ArrayList<>();

        storeInArray();

        try {
            carSpecsAdapter = new CarSpecsAdapter(CarSpecs.this, column_id, curr_car_id, car_sequence, car_changes, curr_date);
            binding.infoRecyclerView.setAdapter(carSpecsAdapter);
            binding.infoRecyclerView.setLayoutManager(new LinearLayoutManager(CarSpecs.this));
        }
        catch (Exception E)
        {

        }

        binding.updateMileageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCarMilestonesDialog(carmileage);
            }
        });

    }
    private void updateCarMilestonesDialog(final String carmileage)
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(CarSpecs.this);

        View mView = getLayoutInflater().inflate(R.layout.update_milestones, null);
        Button cancelNewMilestonesBtn = (Button)mView.findViewById(R.id.cancelNewMilestonesBtn);
        Button changeNewMilestonesBtn = (Button)mView.findViewById(R.id.changeNewMilestonesBtn);
        final EditText newCarMilestonesET = (EditText)mView.findViewById(R.id.newCarMilestonesET);
        mBuilder.setView(mView);

        final AlertDialog updateMilestones = mBuilder.create();

        updateMilestones.show();

        newCarMilestonesET.setText(carmileage);
        cancelNewMilestonesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMilestones.dismiss();
            }
        });

        changeNewMilestonesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(newCarMilestonesET.getText().toString()))
                {
                    newCarMilestonesET.setError("Wypełnij to pole");
                }
                else
                {
                    newCarMilestonesET.setError(null);

                    MyDatabaseHelper myDB = new MyDatabaseHelper(CarSpecs.this);
                    myDB.updateMileage(carid, newCarMilestonesET.getText().toString());
                    updateMilestones.dismiss();
                    binding.carMileageSPECS.setText(spaceBetweenMileageNumbers(newCarMilestonesET.getText().toString())+"km");
                }
            }
        });
    }

    void storeInArray() {

        Cursor cursor = myDB.readDataFromCarInfoTable(carid);
        if (cursor.getCount() == 0) {

        } else {
            while (cursor.moveToNext()) {
                column_id.add(cursor.getString(0));
                curr_car_id.add(cursor.getString(1));
                car_sequence.add(cursor.getString(2));
                car_changes.add(cursor.getString(3));
                curr_date.add(cursor.getString(4));
            }
            cursor.close();
        }
    }

    private String spaceBetweenMileageNumbers(String carmileage)
    {   StringBuilder space = new StringBuilder();

    if(TextUtils.isEmpty(carmileage))
    {

    }
    else {
        if (carmileage.length() > 5) {
            for (int i = 0; i < carmileage.length(); i++) {
                if (i == 3) {
                    space.append(" ");
                }
                space.append(carmileage.charAt(i));
            }

        } else if (carmileage.length() == 5) {
            for (int i = 0; i < carmileage.length(); i++) {
                if (i == 2) {
                    space.append(" ");
                }
                space.append(carmileage.charAt(i));
            }
        } else {
            return carmileage;
        }
    }
        return space.toString();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(CarSpecs.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    protected void onResume() {
        super.onResume();
        storeInArray();

        try {
            carSpecsAdapter = new CarSpecsAdapter(CarSpecs.this, column_id, curr_car_id, car_sequence, car_changes, curr_date);
            binding.infoRecyclerView.setAdapter(carSpecsAdapter);
            binding.infoRecyclerView.setLayoutManager(new LinearLayoutManager(CarSpecs.this));
        }
        catch (Exception E)
        {

        }

    }
}
