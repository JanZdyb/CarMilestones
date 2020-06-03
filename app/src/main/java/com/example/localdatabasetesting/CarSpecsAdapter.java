package com.example.localdatabasetesting;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CarSpecsAdapter extends RecyclerView.Adapter<CarSpecsAdapter.MyViewHolder> {

    private Context context;
    private ArrayList column_id, curr_car_id, car_sequence, car_changes, curr_date;
    private String[] carfixies = {"Wymiana oleju", "Wymiana filtrów", "Wymiana opon na letnie / zimowe", "Przegląd", "Zapłacono za ubezpieczenie"};

    private int position;

    CarSpecsAdapter(Context context, ArrayList column_id, ArrayList curr_car_id, ArrayList car_sequence, ArrayList car_changes, ArrayList curr_date) {

        this.context = context;
        this.column_id = column_id;
        this.curr_car_id = curr_car_id;
        this.car_sequence = car_sequence;
        this.car_changes = car_changes;
        this.curr_date = curr_date;
    }

    @NonNull
    @Override
    public CarSpecsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.car_info, parent, false);
        return new CarSpecsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CarSpecsAdapter.MyViewHolder holder, final int position) {
        this.position = position;

      //  holder.car_id.setText(String.valueOf(car_id.get(position)));
        holder.car_sequence.setText(String.valueOf(car_sequence.get(position)));
        String sequence = (String.valueOf(car_sequence.get(position)));
        holder.car_sequence.setText(readingTheBasicFixies(sequence));
        holder.car_changes.setText(String.valueOf(car_changes.get(position)));
        holder.curr_date.setText(String.valueOf(curr_date.get(position)));

        holder.deleteFromCarInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               deleteFromDatabase();
            }
        });

    }


    @Override
    public int getItemCount() {
        return curr_car_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageButton deleteFromCarInfoBtn;
        TextView column_id, curr_car_id, car_sequence, car_changes, curr_date;
        LinearLayout linearLayoutCarInfo;
        RecyclerView recyclerView;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            recyclerView = itemView.findViewById(R.id.info_recycler_view);
            curr_car_id = itemView.findViewById(R.id.car_id_car_info);
            car_sequence = itemView.findViewById(R.id.basic_changes_car_info);
            car_changes = itemView.findViewById(R.id.all_changes_car_info);
            curr_date = itemView.findViewById(R.id.curr_date_car_info);
            deleteFromCarInfoBtn = itemView.findViewById(R.id.deleteFromCarInfoBtn);
            linearLayoutCarInfo = itemView.findViewById(R.id.linearLayoutCarInfo);
        }
    }

    private String readingTheBasicFixies(String fixies) {
        String[] fixiesArray = {"O", "F", "T", "C", "I"};
        String endString = "";
        for (int i = 0; i < 5; i++) {
            if (fixies.contains(fixiesArray[i])) {
                endString += carfixies[i] + "\n";
            } else {
                continue;
            }
        }
        return endString;
    }

    private void onDeleteAlert() {

        final android.app.AlertDialog.Builder onDeleteAlert = new android.app.AlertDialog.Builder(context);

        onDeleteAlert.setMessage("Na pewno usunąć pozycje?");

        onDeleteAlert.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteFromDatabase();
            }
        });
        onDeleteAlert.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        onDeleteAlert.show();
    }

    private boolean deleteFromDatabase()
    {
        MyDatabaseHelper myDB = new MyDatabaseHelper(context);
        myDB.deleteFromCarsInfo(column_id.get(position).toString());
        return true;
    }

}
