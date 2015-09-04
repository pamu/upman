package com.nagarjuna_pamu.dev.uploadmanager.ui;

import android.app.Activity;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nagarjuna_pamu.dev.uploadmanager.R;
import com.nagarjuna_pamu.dev.uploadmanager.adapters.LocalFilesAdapter;
import com.nagarjuna_pamu.dev.uploadmanager.models.InspectionDetails;
import com.nagarjuna_pamu.dev.uploadmanager.models.LocalDataFile;
import com.nagarjuna_pamu.dev.uploadmanager.models.LocalFilesItem;
import com.nagarjuna_pamu.dev.uploadmanager.models.LocalSeperator;
import com.nagarjuna_pamu.dev.uploadmanager.service.UploaderService;
import com.nagarjuna_pamu.dev.uploadmanager.utils.FileUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LocalFilesFragment.LocalFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LocalFilesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocalFilesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView recyclerView;
    LocalFilesAdapter mLocalFilesAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LocalFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocalFilesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocalFilesFragment newInstance(String param1, String param2) {
        LocalFilesFragment fragment = new LocalFilesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public LocalFilesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_local_files, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.local_files_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mLocalFilesAdapter = new LocalFilesAdapter();
        final LocalFilesAdapter localFilesAdapter = mLocalFilesAdapter;
        recyclerView.setAdapter(localFilesAdapter);

        load();

        Button upload = (Button) root.findViewById(R.id.upload);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(LocalFilesItem localFilesItem : ((LocalFilesAdapter)recyclerView.getAdapter()).localFilesItems) {
                    if (localFilesItem instanceof LocalDataFile) {
                        LocalDataFile localDataFile = ((LocalDataFile) localFilesItem);
                        if (localDataFile.isChecked())
                            UploaderService.startActionUpload(getActivity(), localDataFile.getFile().getAbsolutePath(), localDataFile.getScrapeId());
                    }
                }
            }
        });

        return root;
    }

    public void load() {

        File[] leads = FileUtils.getLeads();
        ArrayList<LocalFilesItem> list = new ArrayList<>();
        for(File lead : leads) {

            LocalSeperator localSeperator = new LocalSeperator();
            localSeperator.setText(lead.getName());
            list.add(localSeperator);

            if (FileUtils.getJsonFile(lead.getName()).length == 1) {
                InspectionDetails inspectionDetails = new InspectionDetails(FileUtils.getInspectorDetails(FileUtils.getJsonFile(lead.getName())[0]));
                list.add(inspectionDetails);
            }

            for(File json : FileUtils.getJsonFile(lead.getName())) {
                LocalDataFile localDataFile = new LocalDataFile();
                localDataFile.setFile(json);
                localDataFile.setScrapeId(lead.getName());
                list.add(localDataFile);
            }

            Log.d("files", lead.getName());

            for(File jpeg : FileUtils.getJpegsFrom(lead)) {
                LocalDataFile jpegFile = new LocalDataFile();
                jpegFile.setFile(jpeg);
                jpegFile.setScrapeId(lead.getName());
                list.add(jpegFile);
            }
        }

        mLocalFilesAdapter.localFilesItems.clear();
        mLocalFilesAdapter.localFilesItems.addAll(list);

        mLocalFilesAdapter.notifyDataSetChanged();
    }
    @Override
    public void onResume() {
        super.onResume();
        load();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onLocalFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (LocalFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface LocalFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onLocalFragmentInteraction(Uri uri);
    }

}
