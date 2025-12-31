package com.tools.module.wheelview;

public interface OnWheelChangedListener {

    void onWheelScrolled(WheelToolsView view, int offset);

    void onWheelSelected(WheelToolsView view, int position);

    void onWheelScrollStateChanged(WheelToolsView view, @ScrollState int state);

    void onWheelLoopFinished(WheelToolsView view);

}