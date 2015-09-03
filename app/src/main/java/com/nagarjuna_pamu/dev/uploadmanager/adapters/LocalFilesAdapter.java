package com.nagarjuna_pamu.dev.uploadmanager.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nagarjuna_pamu.dev.uploadmanager.R;
import com.nagarjuna_pamu.dev.uploadmanager.models.LocalDataFile;
import com.nagarjuna_pamu.dev.uploadmanager.models.LocalFilesItem;
import com.nagarjuna_pamu.dev.uploadmanager.models.LocalSeperator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pnagarjuna on 03/09/15.
 */
public class LocalFilesAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int DATA_ITEM  = 1;
    private static final int SEPERATOR_ITEM = 2;

    public final List<LocalFilesItem> localFilesItems = new ArrayList<>();

    public static class SeperatorViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public SeperatorViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.line_seperator_text);
        }
        public void bindView(LocalSeperator localSeperator) {
            textView.setText(localSeperator.getText());
        }
    }

    public static class LocalDataFileViewHolder extends RecyclerView.ViewHolder {
        private TextView heading;
        private CheckedTextView checkedTextView;
        private ProgressBar progressBar;

        public LocalDataFileViewHolder(View itemView) {
            super(itemView);
            heading = (TextView) itemView.findViewById(R.id.local_scrape_id_heading);
            checkedTextView = (CheckedTextView) itemView.findViewById(R.id.local_file_checked_text_view);
            progressBar = (ProgressBar) itemView.findViewById(R.id.file_upload_progress);
            progressBar.setVisibility(View.GONE);
        }
        public void bindView(LocalDataFile localDataFile) {
            heading.setText(localDataFile.getScrapeId());
            checkedTextView.setText(localDataFile.getFile().getName());
            checkedTextView.setChecked(false);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SEPERATOR_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_seperator, parent, false);
            return new SeperatorViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.local_files_view, parent, false);
            return new LocalDataFileViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (localFilesItems.get(position) instanceof LocalDataFile) {
            ((LocalDataFileViewHolder) holder).bindView(((LocalDataFile) localFilesItems.get(position)));
        } else {
            ((SeperatorViewHolder) holder).bindView((LocalSeperator) localFilesItems.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return localFilesItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (localFilesItems.get(position) instanceof LocalDataFile) {
            return DATA_ITEM;
        } else {
            return SEPERATOR_ITEM;
        }
    }

    @Override
    public long getItemId(int position) {
        return localFilesItems.get(position).hashCode();
    }
}
