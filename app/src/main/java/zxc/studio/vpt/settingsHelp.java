package zxc.studio.vpt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link settingsHelp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class settingsHelp extends Fragment {

    public settingsHelp() {
    }
    public static settingsHelp newInstance() {
        settingsHelp fragment = new settingsHelp();
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
        View view = inflater.inflate(R.layout.fragment_settings_help, container, false);
        return view;
    }
}