package com.lemontree.launcher.layouts;

import com.lemontree.launcher.events.MiniAppInfoClickedListener;
import com.lemontree.launcher.utils.AppInfo;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class MiniModule extends Module{
    private static final double ZOOM_MULTIPLE = 0.6;

    private final List<MiniAppInfoClickedListener> listeners = new ArrayList<>();

    public MiniModule(AppInfo info) {
        super(info);
        reLayout(zoom * ZOOM_MULTIPLE);
    }

    public void addClickedListener(MiniAppInfoClickedListener listener) {
        listeners.add(listener);
    }

    @Override
    public void reLayout(double zoom) {
        super.reLayout(zoom * ZOOM_MULTIPLE);
    }

    @Override
    protected void launch(MouseEvent event) {
        for (MiniAppInfoClickedListener listener : listeners) listener.onAppInfoClicked(info);
    }
}
