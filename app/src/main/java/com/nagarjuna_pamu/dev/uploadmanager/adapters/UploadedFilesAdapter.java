package com.nagarjuna_pamu.dev.uploadmanager.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nagarjuna_pamu.dev.uploadmanager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pnagarjuna on 03/09/15.
 */
public class UploadedFilesAdapter extends RecyclerView.Adapter<UploadedFilesAdapter.UploadedFilesViewHolder> {

    public final List<String> list = new ArrayList<>();

    public static class UploadedFilesViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;
        private View view;
        public UploadedFilesViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            tv = (TextView) view.findViewById(R.id.info_text_remote);
        }
        public void bindView(String data) {
            tv.setText(data);
        }
    }

    @Override
    public UploadedFilesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.remote_files_view, parent, false);
        return new UploadedFilesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UploadedFilesViewHolder holder, int position) {
        holder.bindView(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
