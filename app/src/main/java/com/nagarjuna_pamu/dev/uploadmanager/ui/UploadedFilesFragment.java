package com.nagarjuna_pamu.dev.uploadmanager.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagarjuna_pamu.dev.uploadmanager.R;
import com.nagarjuna_pamu.dev.uploadmanager.adapters.UploadedFilesAdapter;
import com.nagarjuna_pamu.dev.uploadmanager.models.UploadDataFile;
import com.nagarjuna_pamu.dev.uploadmanager.models.UploadSeperator;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UploadedFilesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UploadedFilesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadedFilesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UploadedFilesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadedFilesFragment newInstance(String param1, String param2) {
        UploadedFilesFragment fragment = new UploadedFilesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public UploadedFilesFragment() {
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
        View root = inflater.inflate(R.layout.fragment_uploaded_files, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.uploaded_files_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        UploadedFilesAdapter uploadedFilesAdapter = new UploadedFilesAdapter();
        recyclerView.setAdapter(uploadedFilesAdapter);
        UploadSeperator uploadSeperator = new UploadSeperator();
        uploadSeperator.setText("lead-1111");
        uploadedFilesAdapter.uploadFilesItems.add(uploadSeperator);

        UploadDataFile uploadDataFile = new UploadDataFile();
        uploadDataFile.setFile(new File("/Future.scala"));
        uploadDataFile.setChecked(true);
        uploadDataFile.setDeletable(false);
        uploadDataFile.setScrapeId("lead-11111");
        uploadedFilesAdapter.uploadFilesItems.add(uploadDataFile);

        uploadedFilesAdapter.notifyDataSetChanged();
        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
