package com.startupweekendny.doctorreferrals;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DocListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DocListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    ListView list = null;
    ListAdapter adapter = null;
    Context context = null;

    public DocListFragment() {
        // Required empty public constructor
    }

    public void setData(List<ParseObject> data) {
//        adapter.swapCursor(new QueryCursor(data));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ret = inflater.inflate(R.layout.fragment_doc_list, container, false);
        list = (ListView) ret.findViewById(R.id.doc_list);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("DoctorReferrals");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> resultList, ParseException e) {
                if (e == null) {
                    QueryCursor cursor = new QueryCursor(resultList);
                    List<String> strings = new ArrayList<String>();
                    if (cursor.moveToFirst()) {
                        do {
                            strings.add(cursor.getString(0));
                            cursor.moveToNext();
                        } while (!cursor.isAfterLast());
                    }
//                    adapter = new SimpleCursorAdapter(context, android.R.layout.simple_list_item_1, cursor, new String[]{"name"}, new int[]{android.R.id.text1}, 0);
                    adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, strings);
                    list.setAdapter(adapter);
//                    list.invalidateViews();
                }
            }
        });

        return ret;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
            context = activity;
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
