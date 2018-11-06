package edu.bu.ollie.imageeffect;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ProcImageFragment extends Fragment {

    Integer index;
    Button procButton, revertButton;
    ProcessActivity parentActivity;
    FragmentManager fragmentManager;

    public ProcImageFragment() {
    }

    void updateImgWindow(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.imgWindow, new ImageWindowFragment());
        transaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_proc_image_view, container, false);
        parentActivity = (ProcessActivity)getActivity();
        fragmentManager = getFragmentManager();
        updateImgWindow();
        procButton = (Button)view.findViewById(R.id.procApply);
        revertButton = (Button)view.findViewById(R.id.procRevert);
        procButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.procImg();
                updateImgWindow();
            }
        });
        revertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // parentActivity.previewViewReturn();
            }
        });
        return  view;
    }

}
