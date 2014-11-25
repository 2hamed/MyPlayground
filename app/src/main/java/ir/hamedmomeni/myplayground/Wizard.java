package ir.hamedmomeni.myplayground;

import android.app.Activity;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import butterknife.InjectView;


public class Wizard extends Activity {
    private static final View NULL = null;
    @InjectView(R.id.flipper)
    ViewFlipper vf;
    RelativeLayout wrapper;
    int currentSlide = 1;
    // Using the following method, we will handle all screen swaps.
    int i;
    private float lastX, width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wizard);
        wrapper = (RelativeLayout) findViewById(R.id.wrapper);
        //vf = (ViewFlipper) findViewById(R.id.flipper);
        width = 640; //iv.getMeasuredWidth();
        SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.slidingmenu);
        SlidingMenu menu1 = new SlidingMenu(this);
        menu1.setMode(SlidingMenu.RIGHT);
        menu1.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu1.setShadowWidthRes(R.dimen.shadow_width);
        menu1.setShadowDrawable(R.drawable.shadow);
        menu1.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu1.setFadeDegree(0.35f);
        menu1.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu1.setMenu(R.layout.slidingmenu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wizard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {

            case MotionEvent.ACTION_DOWN:
                lastX = touchevent.getX();
                i = 0;
                break;
            case MotionEvent.ACTION_UP:
                float currentX = touchevent.getX();
                Log.d("slide", ""+currentSlide);
                // Handling left to right screen swap.
                if (lastX > currentX) {

                    // If there aren't any other children, just break.
                    if (currentSlide == 3)
                        break;
                    if(currentSlide == 1){
                        Log.d("step", "1");
                        wrapper.setBackgroundResource(R.drawable.one_two);
                        TransitionDrawable transition = (TransitionDrawable) wrapper.getBackground();
                        transition.startTransition(500);
                    }else{
                        Log.d("step", "2");
                        wrapper.setBackgroundResource(R.drawable.two_three);
                        TransitionDrawable transition = (TransitionDrawable) wrapper.getBackground();
                        transition.startTransition(500);
                    }
                    nextSlide();
                }

                // Handling right to left screen swap.
                if (lastX < currentX) {

                    // If there is a child (to the left), just break.
                    if (currentSlide == 1)
                        break;
                    if(currentSlide == 3){
                        Log.d("step", "3");
                        wrapper.setBackgroundResource(R.drawable.three_two);
                        TransitionDrawable transition = (TransitionDrawable) wrapper.getBackground();
                        transition.startTransition(500);
                    }else{
                        Log.d("step", "4");
                        wrapper.setBackgroundResource(R.drawable.two_one);
                        TransitionDrawable transition = (TransitionDrawable) wrapper.getBackground();
                        transition.startTransition(500);
                    }
                    prevSlide();
                }

                break;
        }
        return false;
    }
    private void nextSlide(){
        vf.setInAnimation(this, R.anim.in_from_right);
        // Current screen goes out from left.
        vf.setOutAnimation(this, R.anim.out_to_left);

        // Display next screen.
        vf.showNext();
        currentSlide++;
    }
    private void prevSlide(){
        // Next screen comes in from left.
        vf.setInAnimation(this, R.anim.in_from_left);
        // Current screen goes out from right.
        vf.setOutAnimation(this, R.anim.out_to_right);
        // Display previous screen.
        vf.showPrevious();
        currentSlide--;
    }
}
