package com.nagarjuna_pamu.dev.uploadmanager.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nagarjuna_pamu.dev.uploadmanager.R;
import com.nagarjuna_pamu.dev.uploadmanager.models.InspectionDetails;
import com.nagarjuna_pamu.dev.uploadmanager.models.LocalDataFile;
import com.nagarjuna_pamu.dev.uploadmanager.models.LocalFilesItem;
import com.nagarjuna_pamu.dev.uploadmanager.models.LocalSeperator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pnagarjuna on 03/09/15.
 */
public class LocalFilesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int DATA_ITEM = 1;
    private static final int SEPERATOR_ITEM = 2;
    private static final int INSPECTOR_ITEM = 3;

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

        public void bindView(final int position, final List<LocalFilesItem> localDataFileList, LocalDataFile localDataFile) {
            heading.setText(localDataFile.getScrapeId());
            checkedTextView.setText(localDataFile.getFile().getName());
            checkedTextView.setChecked(((LocalDataFile) localDataFileList.get(position)).isChecked());
            checkedTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkedTextView.isChecked()) {
                        checkedTextView.setChecked(false);
                        ((LocalDataFile) localDataFileList.get(position)).setChecked(false);
                    } else {
                        checkedTextView.setChecked(true);
                        ((LocalDataFile) localDataFileList.get(position)).setChecked(true);
                    }
                }
            });
        }
    }

    public static class InspectorDetailsViewHolder extends RecyclerView.ViewHolder {
        private TextView scrapeId;
        private TextView make;
        private TextView model;
        private TextView sellerName;
        private TextView sellerPhone;
        private TextView sellerAddress;

        public InspectorDetailsViewHolder(View itemView) {
            super(itemView);
            scrapeId = (TextView) itemView.findViewById(R.id.scrape_id);
            make = (TextView) itemView.findViewById(R.id.make);
            model = (TextView) itemView.findViewById(R.id.model);
            sellerName = (TextView) itemView.findViewById(R.id.seller_name);
            sellerPhone = (TextView) itemView.findViewById(R.id.seller_phone);
            sellerAddress = (TextView) itemView.findViewById(R.id.seller_address);
        }

        public void bindView(final InspectionDetails inspectionDetails) {
            if (inspectionDetails.getScrapeId() != null)
                scrapeId.setText(inspectionDetails.getScrapeId());
            else
                scrapeId.setVisibility(View.GONE);

            if (inspectionDetails.getMake() != null)
                make.setText(inspectionDetails.getMake());
            else make.setVisibility(View.GONE);

            if (inspectionDetails.getModel() != null)
                model.setText(inspectionDetails.getModel());
            else model.setVisibility(View.GONE);

            if (inspectionDetails.getSellerName() != null)
                sellerName.setText(inspectionDetails.getSellerName());
            else sellerName.setVisibility(View.GONE);

            if (inspectionDetails.getSellerPhone() != null)
                sellerPhone.setText(inspectionDetails.getSellerPhone());
            else sellerPhone.setVisibility(View.GONE);

            if (inspectionDetails.getSellerAddress() != null)
                sellerAddress.setText(inspectionDetails.getSellerAddress());
            else sellerAddress.setVisibility(View.GONE);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SEPERATOR_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_seperator, parent, false);
            return new SeperatorViewHolder(view);
        } else if (viewType == DATA_ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.local_files_view, parent, false);
            return new LocalDataFileViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inspection_details_view, parent, false);
            return new InspectorDetailsViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (localFilesItems.get(position) instanceof LocalSeperator) {
            ((SeperatorViewHolder) holder).bindView((LocalSeperator) localFilesItems.get(position));
        } else if (localFilesItems.get(position) instanceof LocalDataFile){
            ((LocalDataFileViewHolder) holder).bindView(position, localFilesItems, ((LocalDataFile) localFilesItems.get(position)));
        } else {
            (( InspectorDetailsViewHolder) holder).bindView((InspectionDetails) localFilesItems.get(position));
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
        } else if (localFilesItems.get(position) instanceof LocalSeperator){
            return SEPERATOR_ITEM;
        } else {
            return INSPECTOR_ITEM;
        }
    }

    @Override
    public long getItemId(int position) {
        return localFilesItems.get(position).hashCode();
    }
}
