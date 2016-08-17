package mobello.amtrust.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mobello.amtrust.com.R;
import mobello.amtrust.com.activity.HealthResultActivity;

public class DamageFragment extends Fragment {

    private TextView continues;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_damage, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        continues = (TextView)view.findViewById(R.id.continues);
        continues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HealthResultActivity.start(getActivity());
                getActivity().finish();
            }
        });
    }
}
