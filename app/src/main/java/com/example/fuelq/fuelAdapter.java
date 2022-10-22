package com.example.fuelq;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fuelq.Model.Fuel;
import java.util.ArrayList;

public class fuelAdapter extends RecyclerView.Adapter<fuelAdapter.ViewHolder> {
    private ArrayList<Fuel> arrayList;
    private Context context;

    public fuelAdapter(ArrayList<Fuel> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public fuelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.get_fuel_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull fuelAdapter.ViewHolder holder, int position) {
        Fuel fuel = arrayList.get(position);
        holder.FuelType.setText(fuel.getFuelType());
        holder.arrivedLitres.setText(fuel.getArrivedLitres() + " Litres");
        holder.remainLitres.setText(fuel.getRemainLitres() + " Litres");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private TextView FuelType, arrivedLitres, remainLitres;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initializing our views with their ids.
            FuelType = itemView.findViewById(R.id.txt_fuel);
            arrivedLitres = itemView.findViewById(R.id.txt_litres);
            remainLitres = itemView.findViewById(R.id.txt_arriving4);

        }
    }

}
