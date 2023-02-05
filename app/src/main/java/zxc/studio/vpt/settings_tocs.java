package zxc.studio.vpt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link settings_tocs#newInstance} factory method to
 * create an instance of this fragment.
 */
public class settings_tocs extends Fragment {

    public settings_tocs() {
    }

    public static settings_tocs newInstance() {
        settings_tocs fragment = new settings_tocs();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_tocs, container, false);
        return view;
    }
}