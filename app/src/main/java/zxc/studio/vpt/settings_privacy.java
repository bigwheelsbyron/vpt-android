package zxc.studio.vpt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link settings_privacy#newInstance} factory method to
 * create an instance of this fragment.
 */
public class settings_privacy extends Fragment {

    public settings_privacy() {
    }

    public static settings_privacy newInstance() {
        settings_privacy fragment = new settings_privacy();
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
        View view = inflater.inflate(R.layout.fragment_settings_privacy, container, false);
        return view;
    }
}