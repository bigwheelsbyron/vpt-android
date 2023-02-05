package zxc.studio.vpt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link settings_aboutus#newInstance} factory method to
 * create an instance of this fragment.
 */
public class settings_aboutus extends Fragment {

    public settings_aboutus() {
    }
    public static settings_aboutus newInstance() {
        settings_aboutus fragment = new settings_aboutus();
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
        View view = inflater.inflate(R.layout.fragment_settings_aboutus, container, false);
        return view;
    }
}