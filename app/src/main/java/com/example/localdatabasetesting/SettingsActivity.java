package com.example.localdatabasetesting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.localdatabasetesting.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {

    ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.settingsToolbar.setLogo(R.drawable.ic_settings);
        binding.settingsToolbar.setTitle("Ustawienia");
        setSupportActionBar(binding.settingsToolbar);

        binding.deleteDatabaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDBAlert();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_up, R.anim.slide_out_up);

    }

    private void deleteDBAlert() {
        final android.app.AlertDialog.Builder deleteDB = new android.app.AlertDialog.Builder(this);
        deleteDB.setMessage("Na pewno chcesz usunąć wszystkie dodane auta?");

        deleteDB.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(SettingsActivity.this);
                myDB.deleteDatabase();
                Toast.makeText(SettingsActivity.this, "Pomyślnie usunięto wszystkie dane", Toast.LENGTH_SHORT).show();
            }
        });
        deleteDB.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        deleteDB.show();

    }
}
