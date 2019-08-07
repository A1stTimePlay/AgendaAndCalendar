package bss.intern.planb.WeekView;

import androidx.annotation.ColorInt;

public interface TextColorPicker {

    @ColorInt
    int getTextColor(WeekViewEvent event);

}
