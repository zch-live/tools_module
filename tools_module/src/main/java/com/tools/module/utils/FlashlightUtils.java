package com.tools.module.utils;

import static android.hardware.Camera.Parameters.FLASH_MODE_OFF;
import static android.hardware.Camera.Parameters.FLASH_MODE_TORCH;

import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;

import java.io.IOException;

public final class FlashlightUtils {

    private static Camera mCamera;
    private static SurfaceTexture mSurfaceTexture;

    private FlashlightUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void setFlashlightStatus(final boolean isOn) {
        if (!init()) return;
        final Camera.Parameters parameters = mCamera.getParameters();
        if (isOn) {
            if (!FLASH_MODE_TORCH.equals(parameters.getFlashMode())) {
                try {
                    mCamera.setPreviewTexture(mSurfaceTexture);
                    mCamera.startPreview();
                    parameters.setFlashMode(FLASH_MODE_TORCH);
                    mCamera.setParameters(parameters);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (!FLASH_MODE_OFF.equals(parameters.getFlashMode())) {
                parameters.setFlashMode(FLASH_MODE_OFF);
                mCamera.setParameters(parameters);
            }
        }
    }

    public static void destroy() {
        if (mCamera == null) return;
        mCamera.release();
        mSurfaceTexture = null;
        mCamera = null;
    }

    private static boolean init() {
        if (mCamera == null) {
            try {
                mCamera = Camera.open(0);
                mSurfaceTexture = new SurfaceTexture(0);
            } catch (Throwable t) {
                Log.e("FlashlightUtils", "init failed: ", t);
                return false;
            }
        }
        if (mCamera == null) {
            Log.e("FlashlightUtils", "init failed.");
            return false;
        }
        return true;
    }
}
