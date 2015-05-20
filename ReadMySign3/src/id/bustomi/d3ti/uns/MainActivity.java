package id.bustomi.d3ti.uns;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.objdetect.CascadeClassifier;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends Activity implements CvCameraViewListener2 {

    private static final String    TAG                 = "ReadMySign3::Activity";
    private static final Scalar    FACE_RECT_COLOR     = new Scalar(0, 255, 0, 255);
    public static final int        JAVA_DETECTOR       = 0;
    public static final int        NATIVE_DETECTOR     = 1;
    
    private Menu menu;

    private MenuItem               mItemFace50;
    private MenuItem               mItemFace40;
    private MenuItem               mItemFace30;
    private MenuItem               mItemFace20;
    private MenuItem               mItemType;

    private Mat                    mRgba;
    private Mat                    mGray;
    private File                   mCascadeFile;
    private CascadeClassifier      mJavaDetector;
    private DetectionBasedTracker  mNativeDetector;

    private int                    mDetectorType       = JAVA_DETECTOR;
    private String[]               mDetectorName;

    private float                  mRelativeFaceSize   = 0.2f;
    private int                    mAbsoluteFaceSize   = 0;

    private CameraView   mOpenCvCameraView;
    private MenuItem[] mResolutionMenuItems;
    private SubMenu mResolutionMenu;
	private List<android.hardware.Camera.Size> mResolutionList;
	
	private HandGesture gesture;

    private BaseLoaderCallback  mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");

                    // Load native library after(!) OpenCV initialization
                    System.loadLibrary("detection_based_tracker");

                    try {
                        // load cascade file from application resources
                        InputStream is = getResources().openRawResource(R.raw.wristdetector2);
                        File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
                        mCascadeFile = new File(cascadeDir, "wristdetector2.xml");
                        FileOutputStream os = new FileOutputStream(mCascadeFile);

                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = is.read(buffer)) != -1) {
                            os.write(buffer, 0, bytesRead);
                        }
                        is.close();
                        os.close();

                        mJavaDetector = new CascadeClassifier(mCascadeFile.getAbsolutePath());
                        if (mJavaDetector.empty()) { 
                            Log.e(TAG, "Failed to load cascade classifier");
                            mJavaDetector = null;
                        } else
                            Log.i(TAG, "Loaded cascade classifier from " + mCascadeFile.getAbsolutePath());

                        mNativeDetector = new DetectionBasedTracker(mCascadeFile.getAbsolutePath(), 0);

                        cascadeDir.delete();

                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(TAG, "Failed to load cascade. Exception thrown: " + e);
                    }

                    mOpenCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public MainActivity() {
        mDetectorName = new String[2];
        mDetectorName[JAVA_DETECTOR] = "Java";
        mDetectorName[NATIVE_DETECTOR] = "Native (tracking)";

        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_main);

		mOpenCvCameraView = (CameraView) findViewById(R.id.maincameraview);
		mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
    }

    public void onDestroy() {
        super.onDestroy();
        mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {
    	
    	if (mGray == null) {
    		mGray = new Mat();			
		}
    	
    	if (mRgba == null) {
    		mRgba = new Mat();		
		}
    	
    	if (gesture == null) {
    		gesture = new HandGesture();	
    		try {
				gesture.openAllImages(getApplicationContext());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
			}
		}
        
        
    }

    public void onCameraViewStopped() {
        mGray.release();
        mRgba.release();
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
    	
        mRgba = inputFrame.rgba();
        mGray = inputFrame.gray();

        if (mAbsoluteFaceSize == 0) {
            int height = mGray.rows();
            if (Math.round(height * mRelativeFaceSize) > 0) {
                mAbsoluteFaceSize = Math.round(height * mRelativeFaceSize);
            }
            mNativeDetector.setMinFaceSize(mAbsoluteFaceSize);
        }
        

        MatOfRect faces = new MatOfRect();

        if (mDetectorType == JAVA_DETECTOR) {
            if (mJavaDetector != null)
                mJavaDetector.detectMultiScale(mGray, faces, 1.1, 2, 2, // TODO: objdetect.CV_HAAR_SCALE_IMAGE
                        new Size(mAbsoluteFaceSize, mAbsoluteFaceSize), new Size());
        }
        else if (mDetectorType == NATIVE_DETECTOR) {
            if (mNativeDetector != null)
                mNativeDetector.detect(mGray, faces);
        }
        else {
            Log.e(TAG, "Detection method is not selected!");
        }
        Rect rect;
        Mat roi ;
        Rect[] facesArray1 = faces.toArray();
        for (int i = 0; i < facesArray1.length; i++){
//            Core.rectangle(mRgba, facesArray1[i].tl(), facesArray1[i].br(),new Scalar(255, 0, 255, 0), 1);
            
            Log.i(TAG, "tl = "+facesArray1[i].tl());
            Log.i(TAG, "br = "+facesArray1[i].br());
            Log.i(TAG, "x = "+facesArray1[i].x);
            Log.i(TAG, "y = "+facesArray1[i].y);
            Log.i(TAG, "width = "+facesArray1[i].width);
            Log.i(TAG, "height = "+facesArray1[i].height);
            
            if (facesArray1[i].width >= mRgba.width() || facesArray1[i].height >= mRgba.height()) 
            	break;				
            
            if (facesArray1.length == 1) {
            	
            	int rowStart=facesArray1[i].y-(100-(facesArray1[i].height/3));
            	int rowEnd=rowStart+100;
            	
            	int y=facesArray1[i].y-(100-(facesArray1[i].height/3));
            	
            	rect=new Rect(facesArray1[i].x-10, y, 100, 100);
            	
            	Core.rectangle(mRgba, rect.tl(), rect.br(), new Scalar(255, 0, 0, 255), 1);
            	Log.i(TAG, "Menggambar kotak merah!");
            	Log.i(TAG, "ROI rect tl = "+rect.tl());
                Log.i(TAG, "ROI rect br = "+rect.br());
                Log.i(TAG, "ROI rect x = "+rect.x);
                Log.i(TAG, "ROI rect y = "+rect.y);
                Log.i(TAG, "ROI rect width = "+rect.width);
                Log.i(TAG, "ROI rect height = "+rect.height);
                Log.i(TAG, "width screen = "+mRgba.width() );
                Log.i(TAG, "height screen = "+mRgba.height());
                
                if (rect.x < 0 || rect.y < 0 || rect.x+100 > mRgba.width() || rect.y+100 > mRgba.height() ) {
					break;
				}
            	
				roi=new Mat(mRgba, rect);
				Log.i(TAG, "Membuat roi!");
				
//				makeContours(roi);
//				if (gesture.cMaxId > -1){
				String hasil=gesture.compare(roi);
				Core.putText(mRgba, hasil, new Point(mRgba.cols()-50,mRgba.rows()-20),
						Core.FONT_HERSHEY_SIMPLEX, 1, new Scalar(255, 255, 255, 255),2);
//				}
				
			}
            
        }
        
        return mRgba;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "called onCreateOptionsMenu");
        this.menu=menu;
		getMenuInflater().inflate(R.menu.main, menu);
//        mItemFace50 = menu.add("Face size 50%");
//        mItemFace40 = menu.add("Face size 40%");
//        mItemFace30 = menu.add("Face size 30%");
//        mItemFace20 = menu.add("Face size 20%");
        mItemType   = menu.add(mDetectorName[mDetectorType]);
        
        mResolutionMenu = menu.addSubMenu("Resolution");
        mResolutionList = mOpenCvCameraView.getResolutionList();
        mResolutionMenuItems = new MenuItem[mResolutionList.size()];
//        mItemType   = menu.add(mDetectorName[mDetectorType]);
        ListIterator<Camera.Size> resolutionItr = mResolutionList.listIterator();
        int idx = 0;
        while(resolutionItr.hasNext()) {
            Camera.Size element = resolutionItr.next();
            mResolutionMenuItems[idx] = mResolutionMenu.add(2, idx, Menu.NONE,
                    Integer.valueOf(element.width).toString() + "x" + Integer.valueOf(element.height).toString());
            idx++;
         }
        
        checkCameraParameters();
        
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "called onOptionsItemSelected; selected item: " + item);
        if (item == mItemFace50)
            setMinFaceSize(0.5f);
        else if (item == mItemFace40)
            setMinFaceSize(0.4f);
        else if (item == mItemFace30)
            setMinFaceSize(0.3f);
        else if (item == mItemFace20)
            setMinFaceSize(0.2f);
        else if (item == mItemType) {
            int tmpDetectorType = (mDetectorType + 1) % mDetectorName.length;
            item.setTitle(mDetectorName[tmpDetectorType]);
            setDetectorType(tmpDetectorType);
        }
        
        int groupId = item.getGroupId();
	    
	    if (item.getGroupId() == 2) {
	    	 int id = item.getItemId();
	            Camera.Size resolution = mResolutionList.get(id);
	            mOpenCvCameraView.setResolution(resolution);
	            resolution = mOpenCvCameraView.getResolution();
	            String caption = Integer.valueOf(resolution.width).toString() + 
	            		"x" + Integer.valueOf(resolution.height).toString();
	            Toast.makeText(this, caption, Toast.LENGTH_SHORT).show();
	            
	            return true;
	    }
	    else if (item == mItemType) {
            int tmpDetectorType = (mDetectorType + 1) % mDetectorName.length;
            item.setTitle(mDetectorName[tmpDetectorType]);
            setDetectorType(tmpDetectorType);
        }
	    return super.onOptionsItemSelected(item);
        
    }
    
    public void checkCameraParameters()
	{
		if (mOpenCvCameraView.isAutoWhiteBalanceLockSupported()) {
			if (mOpenCvCameraView.getAutoWhiteBalanceLock()) {
				Log.d("AutoWhiteBalanceLock", "Locked");
			} else {
				Log.d("AutoWhiteBalanceLock", "Not Locked");
				mOpenCvCameraView.setAutoWhiteBalanceLock(true);
				
				if (mOpenCvCameraView.getAutoWhiteBalanceLock()) {
					Log.d("AutoWhiteBalanceLockAfter", "Locked");
				}
			}
		} else {
			Log.d("AutoWhiteBalanceLock", "Not Supported");
		}		
	}

    private void setMinFaceSize(float faceSize) {
        mRelativeFaceSize = faceSize;
        mAbsoluteFaceSize = 0;
    }

    private void setDetectorType(int type) {
        if (mDetectorType != type) {
            mDetectorType = type;

            if (type == NATIVE_DETECTOR) {
                Log.i(TAG, "Detection Based Tracker enabled");
                mNativeDetector.start();
            } else {
                Log.i(TAG, "Cascade detector enabled");
                mNativeDetector.stop();
            }
        }
    }
    
    void makeContours(Mat img){
    	
    	gesture.contours.clear();
    	Mat src_gray  = new Mat();
		Mat threshold_output  = new Mat();
		
		Imgproc.cvtColor(img, src_gray, Imgproc.COLOR_RGBA2GRAY);
		Imgproc.blur(src_gray, src_gray, new Size(3,3));
		Imgproc.threshold(src_gray, threshold_output, 80, 255, Imgproc.THRESH_BINARY);
		Imgproc.findContours(threshold_output, gesture.contours, gesture.hie,Imgproc.RETR_TREE , 
				Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
		
		gesture.findBiggestContour();
		
		if (gesture.cMaxId > -1) {
			//hg.contours.get(hg.cMaxId) represents the contour of the hand
			Imgproc.drawContours(img, gesture.contours, gesture.cMaxId, FACE_RECT_COLOR, 1);
		}
    }
}
