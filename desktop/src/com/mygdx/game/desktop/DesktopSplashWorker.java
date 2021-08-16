package com.mygdx.game.desktop;

import com.mygdx.game.SplashWorker;

import java.awt.SplashScreen;
/**
 * This class manages the SplashScreen which appears immediately when the Application is opened
 */
public class DesktopSplashWorker implements SplashWorker {
    @Override
    public void closeSplashScreen() {
        SplashScreen splash = SplashScreen.getSplashScreen();
        if(splash != null){
            splash.close();
        }
    }
}
