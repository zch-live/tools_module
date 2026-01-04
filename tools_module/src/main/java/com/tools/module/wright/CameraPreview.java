package com.tools.module.wright;

import static android.content.Context.CAMERA_SERVICE;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import java.util.List;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private Camera mCamera;
    private boolean mPreviewing = false;
    private boolean mSurfaceCreated = false;
    private Delegate mDelegate;
    private CameraManager manager;
    private int cameraId = -1;

    public CameraPreview(Context context) {
        super(context);
        init(context);
    }

    public CameraPreview(Context context, AttributeSet attr) {
        super(context, attr);
        init(context);
    }

    private void init(Context context) {
        manager = (CameraManager) context.getSystemService(CAMERA_SERVICE);
        int numberOfCameras = Camera.getNumberOfCameras();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                mCamera = Camera.open(i);
                cameraId = i;
                break;
            }
        }
        getHolder().addCallback(this);
    }

    void setDelegate(Delegate delegate) {
        mDelegate = delegate;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mSurfaceCreated = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        if (surfaceHolder.getSurface() == null) {
            return;
        }
        stopCameraPreview();
        showCameraPreview();
    }

    public Bitmap rotateMyBitmap(Bitmap bmp) {
        //*****旋转一下
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
//        Bitmap bitmap = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);
        Bitmap nbmp2 = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        return nbmp2;
    }



    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mSurfaceCreated = false;
        Log.i("LZ_TEST", "surfaceDestroyed: ");
        stopCameraPreview();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(mCamera!=null) mCamera.release();
    }

    public void reactNativeShowCameraPreview() {
        if (getHolder() == null || getHolder().getSurface() == null) {
            return;
        }
        stopCameraPreview();
        showCameraPreview();
    }

//    private boolean pause = false;
//    public void pause(){
//        pause = true;
//    }
//    public void resume(){
//        pause = false;
//    }

    public void showCameraPreview() {
        if (mCamera != null) {
            try {
                mPreviewing = true;
                SurfaceHolder surfaceHolder = getHolder();
                surfaceHolder.setKeepScreenOn(true);
                mCamera.setPreviewDisplay(surfaceHolder);
                setDesiredCameraParameters();
                mCamera.startPreview();
                if (mDelegate != null) {
                    mDelegate.onStartPreview();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("LZ_TEST", "showCameraPreview: Err:"+e.getLocalizedMessage());
            }
        }
    }

    public void stopCameraPreview() {
        Log.i("LZ_TEST", "stopCameraPreview: ");
        if (mCamera != null) {
            try {
                mPreviewing = false;
                mCamera.cancelAutoFocus();
                mCamera.setOneShotPreviewCallback(null);
                mCamera.stopPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private List<Integer> radios;
    public List<Integer> initScaleList(){
        if(radios==null){
            if(mCamera!=null){
                radios = mCamera.getParameters().getZoomRatios();
            }
        }
        return radios;
    }

    public void setScale(int scale){
        if(mCamera!=null){
            try {
                List<Integer> integers = initScaleList();
                Log.i("LZ_TEST", "setScale: scale");
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setZoom(scale);
                mCamera.setParameters(parameters);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    private boolean flashLightAvailable() {
        return isPreviewing() && getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    boolean isPreviewing() {
        return mCamera != null && mPreviewing && mSurfaceCreated;
    }

    interface Delegate {
        void onStartPreview();
    }

    private int[] selectPreviewFpsRange(Camera camera, float desiredPreviewFps) {
        // The camera API uses integers scaled by a factor of 1000 instead of floating-point frame
        // rates.
        int desiredPreviewFpsScaled = (int) (desiredPreviewFps * 1000.0f);

        // The method for selecting the best range is to minimize the sum of the differences between
        // the desired value and the upper and lower bounds of the range.  This may select a range
        // that the desired value is outside of, but this is often preferred.  For example, if the
        // desired frame rate is 29.97, the range (30, 30) is probably more desirable than the
        // range (15, 30).
        int[] selectedFpsRange = null;
        int minDiff = Integer.MAX_VALUE;
        List<int[]> previewFpsRangeList = camera.getParameters().getSupportedPreviewFpsRange();
        for (int[] range : previewFpsRangeList) {
            int deltaMin = desiredPreviewFpsScaled - range[Camera.Parameters.PREVIEW_FPS_MIN_INDEX];
            int deltaMax = desiredPreviewFpsScaled - range[Camera.Parameters.PREVIEW_FPS_MAX_INDEX];
            int diff = Math.abs(deltaMin) + Math.abs(deltaMax);
            if (diff < minDiff) {
                selectedFpsRange = range;
                minDiff = diff;
            }
        }
        return selectedFpsRange;
    }

    void setDesiredCameraParameters() {
        Camera.Parameters parameters = mCamera.getParameters();
        Point resolution = getScreenResolution(getContext());
        int x = resolution.x;
        resolution.x = resolution.y;
        resolution.y = x;
        Point result = getPreviewResolution(mCamera.getParameters(), resolution);
        parameters.setPreviewSize(result.x, result.y);
        // https://github.com/googlesamples/android-vision/blob/master/visionSamples/barcode-reader/app/src/main/java/com/google/android/gms/samples/vision/barcodereader/ui/camera/CameraSource.java
        int[] previewFpsRange = selectPreviewFpsRange(mCamera, 60.0f);
        if (previewFpsRange != null) {
            parameters.setPreviewFpsRange(
                    previewFpsRange[Camera.Parameters.PREVIEW_FPS_MIN_INDEX],
                    previewFpsRange[Camera.Parameters.PREVIEW_FPS_MAX_INDEX]);
        }
        int orientation = getDisplayOrientation();
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        mCamera.setDisplayOrientation(orientation);
        mCamera.setParameters(parameters);
    }

    private int getDisplayOrientation() {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, info);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return 0;
        }
        Display display = wm.getDefaultDisplay();

        int rotation = display.getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;
        } else {
            result = (info.orientation - degrees + 360) % 360;
        }
        return result;
    }

    static Point getScreenResolution(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point screenResolution = new Point();
        display.getSize(screenResolution);
        return screenResolution;
    }

    private static Point getPreviewResolution(Camera.Parameters parameters, Point screenResolution) {
        Point previewResolution =
                findBestPreviewSizeValue(parameters.getSupportedPreviewSizes(), screenResolution);
        if (previewResolution == null) {
            previewResolution = new Point((screenResolution.x >> 3) << 3, (screenResolution.y >> 3) << 3);
        }
        return previewResolution;
    }

    private static Point findBestPreviewSizeValue(List<Camera.Size> supportSizeList, Point screenResolution) {
        int bestX = 0;
        int bestY = 0;
        int diff = Integer.MAX_VALUE;
        for (Camera.Size previewSize : supportSizeList) {
            int newX = previewSize.width;
            int newY = previewSize.height;

            int newDiff = Math.abs(newX - screenResolution.x) + Math.abs(newY - screenResolution.y);
            if (newDiff == 0) {
                bestX = newX;
                bestY = newY;
                break;
            } else if (newDiff < diff) {
                bestX = newX;
                bestY = newY;
                diff = newDiff;
            }

        }

        if (bestX > 0 && bestY > 0) {
            return new Point(bestX, bestY);
        }
        return null;
    }
}