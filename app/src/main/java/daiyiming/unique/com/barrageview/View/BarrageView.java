package daiyiming.unique.com.barrageview.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Random;

import daiyiming.unique.com.barrage.R;
import daiyiming.unique.com.barrage.screenparams.ScreenParams;

/**
 * Created by daiyiming on 2015/11/15.
 */
public class BarrageView extends FrameLayout {

    private final static int PLAY_DURATION = 400;
    private final static int DISAPPEAR_DURATION = 15000;

    private TextView tv_tag = null;
    private ScreenParams screenParams = null;
    private boolean isMessured = false;
    private Random random = null;

    private int[] colors = new int[] {
            Color.parseColor("#e51c23"),
            Color.parseColor("#e91e63"),
            Color.parseColor("#9c27b0"),
            Color.parseColor("#673ab7"),
            Color.parseColor("#3f51b5"),
            Color.parseColor("#5677fc"),
            Color.parseColor("#ffc107"),
            Color.parseColor("#009688"),
            Color.parseColor("#259b24"),
    };

    private String[] texts = new String[] {
            "Android上也可以有弹幕哟~",
            "是不是还挺有意思的~",
            "我也这么觉的~",
            "我们的APP特别的有意思~",
            "我只是吐槽一下产品~",
            "这个地方不要写死，以后一定会改的~",
            "***是这个世界上最好的语言!!",
            "请叫我攻城狮~~",
            "卖个萌~O(∩_∩)O~~"
    };

    private Runnable playRunnable = new Runnable() {
        @Override
        public void run() {
            final TextView tem = new TextView(getContext());
            tem.setTextSize(18);
            tem.setBackgroundResource(R.drawable.drawable_barrage_item_background);
            tem.setTextColor(colors[random.nextInt(colors.length)]);
            String text = texts[random.nextInt(texts.length)];
            tem.setText(text);
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.RIGHT;
            tem.setLayoutParams(params);
            BarrageView.this.addView(tem);
            tem.setY(random.nextInt(screenParams.getScreenHeight() / 4) + screenParams.getScreenHeight() / 3);

            ObjectAnimator animator = ObjectAnimator.ofFloat(tem, "translationX", 0, 0 - screenParams.getScreenWidth());
            animator.setDuration(2400);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    BarrageView.this.removeView(tem);
                }
            });
            animator.start();

            handler.postDelayed(playRunnable, PLAY_DURATION);
        }
    };
    private Runnable disappearRunnable = new Runnable() {
        @Override
        public void run() {
            ObjectAnimator animator = ObjectAnimator.ofFloat(BarrageView.this, "alpha", 1, 0);
            animator.setDuration(300);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    BarrageView.this.setVisibility(GONE);
                }
            });
            animator.start();
        }
    };
    private Handler handler = new Handler();

    public BarrageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.setBackgroundResource(R.drawable.drawable_barrage_background);

        tv_tag = new TextView(getContext());
        tv_tag.setTextSize(18);
        tv_tag.setTextColor(Color.WHITE);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());
        params.bottomMargin = margin;
        params.leftMargin = margin;
        params.rightMargin = margin;
        tv_tag.setSingleLine(false);
        tv_tag.setLayoutParams(params);
        tv_tag.setText("没错这就是弹幕~一会就没了~");
        this.addView(tv_tag);

        screenParams = new ScreenParams(getContext());
        random = new Random();

        ViewTreeObserver observer = getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (!isMessured) {
                    isMessured = true;
                    handler.postDelayed(playRunnable, PLAY_DURATION);
                    handler.postDelayed(disappearRunnable, DISAPPEAR_DURATION);
                }
                return true;
            }
        });
    }
}
