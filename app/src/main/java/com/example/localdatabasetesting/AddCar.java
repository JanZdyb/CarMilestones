package com.example.localdatabasetesting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.localdatabasetesting.databinding.ActivityAddCarBinding;

public class AddCar extends AppCompatActivity {

    private ActivityAddCarBinding binding;
    private int selectedId = 0;
    private String carFuel = "";
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddCarBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.addCarToolbar.setTitle("Dodaj nowe auto");
        binding.addCarToolbar.setLogo(R.drawable.ic_add);
        setSupportActionBar(binding.addCarToolbar);

        binding.addNewCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!checkValidate()) {

                }
                else
                {
                    if (binding.mainRadioGroup.getCheckedRadioButtonId() != -1) {
                        selectedId = binding.mainRadioGroup.getCheckedRadioButtonId();
                        radioButton = (RadioButton) findViewById(selectedId);
                        carFuel = (String) radioButton.getText();

                        String carname = binding.carNameEt.getText().toString();
                        String carmodel = binding.carModelEt.getText().toString();
                        String carmileage = binding.carMileageEt.getText().toString();
                        int _carmileage = Integer.parseInt(carmileage);
                        String carfuel = carFuel;
                        String carengine = binding.carEngineEt.getText().toString();
                        String carvin = binding.carVinEt.getText().toString();

                        MyDatabaseHelper myDB = new MyDatabaseHelper(AddCar.this);
                        myDB.addCar(carname, carmodel, _carmileage, carfuel, carengine, carvin);
                        goBack();
                    }
                    else
                    {
                        Toast.makeText(AddCar.this, "Proszę wybrać paliwo", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }


    private boolean checkValidate()
    {       boolean valid = true;

        String carname = binding.carNameEt.getText().toString();
        if(TextUtils.isEmpty(carname) || carname.length() > 20)
        {
            binding.carNameEt.setError("Nazwa pojazdu do 20 znaków");
            valid = false;
        }
        else
        {
            binding.carNameEt.setError(null);
        }

        String carmodel = binding.carModelEt.getText().toString();
        if(TextUtils.isEmpty(carmodel) || carmodel.length() > 30)
        {
            binding.carModelEt.setError("Nazwa modelu do 30 znaków");
            valid = false;
        }
        else
        {
            binding.carModelEt.setError(null);
        }

        String carmileage = binding.carMileageEt.getText().toString();
        if(TextUtils.isEmpty(carmileage) || carmileage.length() > 8)
        {
            binding.carMileageEt.setError("Przebieg do 8 pozycji");
            valid = false;
        }
        else
        {
            binding.carMileageEt.setError(null);
        }

        String carengine = binding.carEngineEt.getText().toString();
        if(TextUtils.isEmpty(carengine) || carengine.length() > 10)
        {
            binding.carEngineEt.setError("Nazwa silnika do 10 znaków");
            valid = false;
        }
        else
        {
            binding.carEngineEt.setError(null);
        }

        String carvin = binding.carVinEt.getText().toString();
        if(TextUtils.isEmpty(carvin) || carvin.length() > 17)
        {
            binding.carVinEt.setError("VIN pojazdu do 17 znaków");
            valid = false;
        }
        else
        {
            binding.carVinEt.setError(null);
        }

        return valid;
    }

    private void goBack()
    {
        Intent intent = new Intent(AddCar.this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_out, R.anim.slide_out_down);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddCar.this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_out, R.anim.slide_out_down);

    }
}
