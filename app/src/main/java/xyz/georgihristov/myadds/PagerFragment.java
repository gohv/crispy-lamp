package xyz.georgihristov.myadds;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by gohv on 02.03.17.
 */
public class PagerFragment extends Fragment {

    public static final String KEY_INDEX = "KEY_INDEX";

    private int page;

    public static PagerFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(KEY_INDEX, page);
        PagerFragment fragment = new PagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt(KEY_INDEX);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_layout, container, false);
        TextView textView = (TextView) view.findViewById(R.id.page_number_label);
        textView.setText("Fragment #" + page);
        return view;
    }
}
