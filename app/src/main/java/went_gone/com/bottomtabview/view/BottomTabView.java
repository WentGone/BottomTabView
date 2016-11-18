package went_gone.com.bottomtabview.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.TextView;

import went_gone.com.bottomtabview.R;


/**
 * Describe:底部Tab选项卡
 * Author: Created by Went_Gone on 2016/11/18.
 */

public class BottomTabView extends FrameLayout implements Checkable{
    private Context context;
    private TextView mTVtab;
    private TextView mTVcount;
    private boolean isChecked;
    private float tabSize;
    public BottomTabView(Context context) {
        this(context,null);
    }

    public BottomTabView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BottomTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        View.inflate(context, R.layout.bottom_tab_view,this);
        mTVtab = (TextView) findViewById(R.id.tab_item_label);
        mTVcount = (TextView) findViewById(R.id.tab_item_count);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BottomTabView);
        Drawable background = array.getDrawable(R.styleable.BottomTabView_tab_src);
        String label_Str = array.getString(R.styleable.BottomTabView_tab_text);
        tabSize = array.getDimensionPixelSize(R.styleable.BottomTabView_tab_textSize,39);
        if (label_Str != null){
            if (null != background) {
                background.setBounds(0, 0, background.getMinimumWidth(), background.getMinimumHeight());
                mTVtab.setCompoundDrawables(null, background, null, null);
            }
            mTVtab.setText(label_Str);
            mTVtab.setTextSize(px2sp(context,tabSize));
            ColorStateList colorStateList = array.getColorStateList(R.styleable.BottomTabView_tab_textColor);
            if (colorStateList!=null){
                mTVtab.setTextColor(colorStateList);
            }
        }else {
            background.setBounds(0, 0, background.getMinimumWidth(), background.getMinimumHeight());
            mTVtab.setCompoundDrawables(null, background, null, null);
            mTVtab.setCompoundDrawablePadding((int) -tabSize);
        }
        int iCount = array.getInt(R.styleable.BottomTabView_tab_count, 0);
        if (iCount != 0) {
            mTVcount.setText(String.valueOf(iCount));
        } else {
            mTVcount.setVisibility(View.GONE);
        }
        array.recycle();
    }

    @Override
    public void setChecked(boolean b) {
        if (isChecked != b){
            isChecked = b;
            mTVtab.setSelected(isChecked);
            setSelected(isChecked);
        }
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    private int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    private int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}
