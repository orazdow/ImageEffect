package edu.bu.ollie.imageeffect.image;

import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.app.Fragment;
import edu.bu.ollie.imageeffect.ProcImageFragment;
import edu.bu.ollie.imageeffect.ProcessActivity;

public abstract class CtlFragment extends Fragment {
    ProcessActivity parentActivity;
    ProcImageFragment parentFragment;
    ImageProcessor processor;
    Handler handler;
    HandlerThread handlerThread;
    int pending = 0;

    public abstract void resetControls();

    @Override
    public void onResume() {
        super.onResume();
        processor.resetParams();
    }

    public void onCreateInit(){
        handlerThread = new HandlerThread("handlerthread");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
        parentActivity = (ProcessActivity) getActivity();
        parentFragment = parentActivity.procViewFragment;
        processor = parentActivity.processor;
    }

    public void handleUpdate(){
        if(pending <= 1){
            pending++;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    processor.process();
                    parentFragment.updateImgWindow();
                    if(pending > 0){pending--;}
                }
            });
        }

    }
}
