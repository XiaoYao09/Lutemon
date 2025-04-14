package com.example.lutemonapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.ViewHolder> {

    private List<Lutemon> lutemons;

    public TrainingAdapter(List<Lutemon> lutemons) {
        this.lutemons = lutemons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_training_lutemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lutemon l = lutemons.get(position);
        holder.txtInfo.setText(
                l.getName() + " (" + l.getColor() + ")\n" +
                        "ATK: " + l.getAttack() + " | DEF: " + l.getDefense() + " | HP: " + l.getHealth() + "/" + l.getMaxHealth() + "\n" +
                        "EXP: " + l.getExperience() + " | SPD: " + l.getSpeed()
        );

        holder.btnTrain.setOnClickListener(v -> {
            l.gainExperience();
            notifyItemChanged(position);
            Toast.makeText(v.getContext(), "Trained! EXP +1", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return lutemons.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtInfo;
        Button btnTrain;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtInfo = itemView.findViewById(R.id.txtInfo); // 修正 ID
            btnTrain = itemView.findViewById(R.id.btnTrain);
        }
    }
}

