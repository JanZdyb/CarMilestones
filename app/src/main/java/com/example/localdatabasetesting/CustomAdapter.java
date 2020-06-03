package com.example.localdatabasetesting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList car_id, car_name, car_model, car_mileage, car_fuel, car_engine, car_vin;
    private int position;

    CustomAdapter(Context context, ArrayList car_id, ArrayList car_name, ArrayList car_model, ArrayList car_mileage, ArrayList car_fuel, ArrayList car_engine, ArrayList car_vin) {

        this.context = context;
        this.car_id = car_id;
        this.car_name = car_name;
        this.car_model = car_model;
        this.car_mileage = car_mileage;
        this.car_fuel = car_fuel;
        this.car_engine = car_engine;
        this. car_vin = car_vin;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, final int position) {
        this.position = position;

        holder.car_id.setText(String.valueOf(car_id.get(position)));
        holder.car_name.setText(String.valueOf(car_name.get(position)));
        holder.car_model.setText(String.valueOf(car_model.get(position)));
      //  holder.car_mileage.setText(String.valueOf(car_mileage.get(position)));
      //  holder.car_fuel.setText(String.valueOf(car_fuel.get(position)));
        holder.car_engine.setText(String.valueOf(car_engine.get(position)));
      //  holder.car_vin.setText(String.valueOf(car_vin.get(position)));

        holder.linear_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CarSpecs.class);
                intent.putExtra("ID", String.valueOf(car_id.get(position)));
                intent.putExtra("NAME", String.valueOf(car_name.get(position)));
                intent.putExtra("MODEL", String.valueOf(car_model.get(position)));
                intent.putExtra("MILEAGE", String.valueOf(car_mileage.get(position)));
                intent.putExtra("FUEL", String.valueOf(car_fuel.get(position)));
                intent.putExtra("ENGINE", String.valueOf(car_engine.get(position)));
                intent.putExtra("VIN", String.valueOf(car_vin.get(position)));
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                ((Activity) context).finish();
            }
        });
    }


    @Override
    public int getItemCount() {
        return car_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView car_id, car_name, car_model, car_mileage, car_fuel, car_engine, car_vin;
        LinearLayout linear_layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            car_id = itemView.findViewById(R.id.car_id_ROW);
            car_name = itemView.findViewById(R.id.car_name_ROW);
            car_model = itemView.findViewById(R.id.car_model_ROW);
         //   car_mileage = itemView.findViewById(R.id.car_mileage_ROW);
         //   car_fuel = itemView.findViewById(R.id.car_fuel_ROW);
            car_engine = itemView.findViewById(R.id.car_engine_ROW);
         //   car_vin = itemView.findViewById(R.id.car_vin_ROW);
            linear_layout = itemView.findViewById(R.id.linear_layout);
        }
    }
}
