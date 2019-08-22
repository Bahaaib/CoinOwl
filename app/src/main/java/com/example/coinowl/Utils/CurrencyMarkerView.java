package com.example.coinowl.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;
import com.example.coinowl.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

@SuppressLint("ViewConstructor")
public class CurrencyMarkerView extends MarkerView {

    private MPPointF offset;
    private TextView tvContent;

    public CurrencyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        tvContent = findViewById(R.id.tvContent);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        tvContent.setText(String.valueOf(e.getY()));

        super.refreshContent(e, highlight);

    }

    @Override
    public MPPointF getOffset() {

        if (offset == null) {
            offset = new MPPointF(-(getWidth() >> 1), -(getHeight()));
        }

        return offset;
    }
}
