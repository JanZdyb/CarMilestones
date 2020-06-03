package com.example.localdatabasetesting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.localdatabasetesting.databinding.ActivityAddNewCarInfoBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddNewCarInfo extends AppCompatActivity {

    private ActivityAddNewCarInfoBinding binding;
    private String FixSequence = "";
    private String OtherFixies = "";
    private String carid, name, model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddNewCarInfoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        carid = getIntent().getStringExtra("CARID");
        name = getIntent().getStringExtra("NAME");
        model = getIntent().getStringExtra("MODEL");
        binding.carNameTextView.setText(name + " " + model);

        binding.addNewCarInfoToolbar.setTitle("Wykonane naprawy");
        binding.addNewCarInfoToolbar.setLogo(R.drawable.ic_done_works);
        setSupportActionBar(binding.addNewCarInfoToolbar);

        binding.addCarInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(binding.oilChangedCheckbox.isChecked()) FixSequence += "O";
                if(binding.filterChangedCheckbox.isChecked()) FixSequence += "F";
                if(binding.tiresChangedCheckbox.isChecked()) FixSequence += "T";
                if(binding.carReviewCheckbox.isChecked()) FixSequence += "C";
                if(binding.insurancePayCheckbox.isChecked()) FixSequence += "I";

                if(TextUtils.isEmpty(binding.otherFixesEditText.getText().toString()))
                {

                }
                else
                {
                    OtherFixies = binding.otherFixesEditText.getText().toString();
                }

                if(TextUtils.isEmpty(FixSequence) && TextUtils.isEmpty(binding.otherFixesEditText.getText().toString()))
                {
                    Toast.makeText(AddNewCarInfo.this, "Nie możesz dodać pustej naprawy.", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    MyDatabaseHelper myDB = new MyDatabaseHelper(AddNewCarInfo.this);
                    myDB.addInfo(carid, FixSequence, OtherFixies, getCurrentDate());
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                }

            }
        });
    }

    private String getCurrentDate()
    {
        Calendar date = Calendar.getInstance();
        SimpleDateFormat printedFormat = new SimpleDateFormat("dd MMMM yyyy");

        return printedFormat.format(date.getTime());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

}
