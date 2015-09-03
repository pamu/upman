package com.nagarjuna_pamu.dev.uploadmanager.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.nagarjuna_pamu.dev.uploadmanager.R;
import com.nagarjuna_pamu.dev.uploadmanager.models.UploadDataFile;
import com.nagarjuna_pamu.dev.uploadmanager.models.UploadFilesItem;
import com.nagarjuna_pamu.dev.uploadmanager.models.UploadSeperator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pnagarjuna on 03/09/15.
 */
public class UploadedFilesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final List<UploadFilesItem> uploadFilesItems = new ArrayList<>();
    private final static int SEPERATOR_ITEM = 1;
    private final static int DATA_ITEM = 2;

    public static class UploadFilesSeperatorViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public UploadFilesSeperatorViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.line_seperator_text);
        }

        public void bindView(UploadSeperator uploadSeperator) {
            textView.setText(uploadSeperator.getText());
        }
    }

    public static class UploadDataFileViewHolder extends RecyclerView.ViewHolder {
        private TextView heading;
        private CheckedTextView checkedTextView;

        public UploadDataFileViewHolder(View itemView) {
            super(itemView);
            heading = (TextView) itemView.findViewById(R.id.remote_scrape_id_heading);
            checkedTextView = (CheckedTextView) itemView.findViewById(R.id.remote_file_checked_text_view);
        }

        public void bindView(final int position, final List<UploadFilesItem> uploadFilesItems, final UploadDataFile uploadDataFile) {
            heading.setText(uploadDataFile.getScrapeId());
            checkedTextView.setEnabled(((UploadDataFile) uploadFilesItems.get(position)).isDeletable());
            checkedTextView.setChecked(((UploadDataFile) uploadFilesItems.get(position)).isChecked());
            checkedTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkedTextView.isChecked()) {
                        checkedTextView.setChecked(false);
                        ((UploadDataFile) uploadFilesItems.get(position)).setChecked(false);
                    } else {
                        checkedTextView.setChecked(true);
                        ((UploadDataFile) uploadFilesItems.get(position)).setChecked(true);
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SEPERATOR_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_seperator, parent, false);
            return new UploadFilesSeperatorViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.remote_files_view, parent, false);
            return new UploadDataFileViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (uploadFilesItems.get(position) instanceof UploadDataFile) {
            ((UploadDataFileViewHolder) holder).bindView(position, uploadFilesItems, ((UploadDataFile) uploadFilesItems.get(position)));
        } else {
            ((UploadFilesSeperatorViewHolder) holder).bindView(((UploadSeperator) uploadFilesItems.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return uploadFilesItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (uploadFilesItems.get(position) instanceof UploadDataFile) {
            return DATA_ITEM;
        } else {
            return SEPERATOR_ITEM;
        }
    }
}
