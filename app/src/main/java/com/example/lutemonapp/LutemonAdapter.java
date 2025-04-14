package com.example.lutemonapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LutemonAdapter extends RecyclerView.Adapter<LutemonAdapter.ViewHolder> {

    private List<Lutemon> lutemons;

    public LutemonAdapter(List<Lutemon> lutemons) {
        this.lutemons = lutemons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lutemon, parent, false);
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
            Storage.getInstance().moveLutemon(l.getId(), "home", "training");
            lutemons.remove(position);
            notifyItemRemoved(position);
            Toast.makeText(v.getContext(), "Moved to Training", Toast.LENGTH_SHORT).show();
        });

        holder.btnBattle.setOnClickListener(v -> {
            Storage.getInstance().moveLutemon(l.getId(), "home", "battle");
            lutemons.remove(position);
            notifyItemRemoved(position);
            Toast.makeText(v.getContext(), "Moved to Battle", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return lutemons.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtInfo;
        Button btnTrain, btnBattle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtInfo = itemView.findViewById(R.id.txtInfo);
            btnTrain = itemView.findViewById(R.id.btnTrain);
            btnBattle = itemView.findViewById(R.id.btnBattle);
        }
    }
}

