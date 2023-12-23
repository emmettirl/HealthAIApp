package com.example.healthaiapp.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.example.healthaiapp.R;

import java.util.ArrayList;
import java.util.List;

public class SymptomRecyclerAdapter extends RecyclerView.Adapter<SymptomRecyclerAdapter.ViewHolder> {

    private final List<SymptomListItem> itemList = new ArrayList<>();

    public SymptomRecyclerAdapter(List<String> symptomLabels) {
        updateData(symptomLabels);
    }

    public void updateData(List<String> symptomLabels) {
        itemList.clear();
        for (String symptomLabel : symptomLabels) {
            itemList.add(new SymptomListItem(symptomLabel));
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SymptomListItem item = itemList.get(position);
        holder.checkBox.setText(item.getName());
        holder.checkBox.setChecked(item.isSelected());

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            itemList.get(holder.getAdapterPosition()).setSelected(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.checkBox.setOnCheckedChangeListener(null);
    }

    // ViewHolder inner class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;

        public ViewHolder(View view) {
            super(view);
            checkBox = view.findViewById(R.id.checkBox);
        }
    }

    public List<SymptomListItem> getSelectedItems() {
        List<SymptomListItem> selectedItems = new ArrayList<>();
        for (SymptomListItem item : itemList) {
            if (item.isSelected()) {
                selectedItems.add(item);
            }
        }
        return selectedItems;
    }
}
