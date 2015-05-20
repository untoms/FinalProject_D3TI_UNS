package id.bustomi.d3ti.uns;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import android.content.Context;
import android.util.Log;

public class HandGesture {
	
	private static final String    TAG                 = "ReadMySign3::HandGesture";
	public List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
	public int cMaxId = -1;
	public Mat hie = new Mat();
	private List<Mat> all_letter=new ArrayList<Mat>();
	private String letters []={"A","B","C","D","E","F","G","H","I","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","I Love U"};
	private List<List<MatOfPoint>> all_letters=new ArrayList<List<MatOfPoint>>();
	private Mat mReferenceImage;
	public int lMaxId = -1;
	
	public void openAllImages(final Context context) throws IOException{
		
		mReferenceImage = Utils.loadResource(context, R.drawable.a, Highgui.CV_LOAD_IMAGE_COLOR);
		
		if (!mReferenceImage.empty()) {
			Mat src_gray  = new Mat();
			Mat threshold_output  = new Mat();
			List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
			
			Imgproc.cvtColor(mReferenceImage, src_gray,Imgproc.COLOR_BGR2GRAY );
			Imgproc.blur(src_gray, src_gray, new Size(3,3));
			Imgproc.threshold(src_gray, threshold_output, 100, 255, Imgproc.THRESH_BINARY);
			Imgproc.findContours(threshold_output, contours, hie,Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
			
			all_letter.add(threshold_output);				
			all_letters.add(contours);
			Log.i(TAG, "Letter : A -- "+mReferenceImage.toString());
		}
		
		mReferenceImage = Utils.loadResource(context, R.drawable.b, Highgui.CV_LOAD_IMAGE_COLOR);
		
		if (!mReferenceImage.empty()) {
			
			Mat src_gray  = new Mat();
			Mat threshold_output  = new Mat();
			List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
			
			Imgproc.cvtColor(mReferenceImage, src_gray,Imgproc.COLOR_BGR2GRAY );
			Imgproc.blur(src_gray, src_gray, new Size(3,3));
			Imgproc.threshold(src_gray, threshold_output, 100, 255, Imgproc.THRESH_BINARY);
			Imgproc.findContours(threshold_output, contours, hie,Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
			
			all_letter.add(threshold_output);	
			all_letters.add(contours);
			Log.i(TAG, "Letter : B -- "+mReferenceImage.toString());
		}
		
		mReferenceImage = Utils.loadResource(context, R.drawable.c, Highgui.CV_LOAD_IMAGE_COLOR);
		
		if (!mReferenceImage.empty()) {
			
			Mat src_gray  = new Mat();
			Mat threshold_output  = new Mat();
			List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
			
			Imgproc.cvtColor(mReferenceImage, src_gray,Imgproc.COLOR_BGR2GRAY );
			Imgproc.blur(src_gray, src_gray, new Size(3,3));
			Imgproc.threshold(src_gray, threshold_output, 100, 255, Imgproc.THRESH_BINARY);
			Imgproc.findContours(threshold_output, contours, hie,Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
			
			all_letter.add(threshold_output);	
			all_letters.add(contours);
			Log.i(TAG, "Letter : C -- "+mReferenceImage.toString());
		}
		
		mReferenceImage = Utils.loadResource(context, R.drawable.d, Highgui.CV_LOAD_IMAGE_COLOR);
		
		if (!mReferenceImage.empty()) {
			
			Mat src_gray  = new Mat();
			Mat threshold_output  = new Mat();
			List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
			
			Imgproc.cvtColor(mReferenceImage, src_gray,Imgproc.COLOR_BGR2GRAY );
			Imgproc.blur(src_gray, src_gray, new Size(3,3));
			Imgproc.threshold(src_gray, threshold_output, 100, 255, Imgproc.THRESH_BINARY);
			Imgproc.findContours(threshold_output, contours, hie,Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
			
			all_letter.add(threshold_output);	
			all_letters.add(contours);
			Log.i(TAG, "Letter : D -- "+mReferenceImage.toString());
		}
		
		mReferenceImage = Utils.loadResource(context, R.drawable.e, Highgui.CV_LOAD_IMAGE_COLOR);
		
		if (!mReferenceImage.empty()) {
			
			Mat src_gray  = new Mat();
			Mat threshold_output  = new Mat();
			List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
			
			Imgproc.cvtColor(mReferenceImage, src_gray,Imgproc.COLOR_BGR2GRAY );
			Imgproc.blur(src_gray, src_gray, new Size(3,3));
			Imgproc.threshold(src_gray, threshold_output, 100, 255, Imgproc.THRESH_BINARY);
			Imgproc.findContours(threshold_output, contours, hie,Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
			
			all_letter.add(threshold_output);	
			all_letters.add(contours);
			Log.i(TAG, "Letter : E -- "+mReferenceImage.toString());
		}
		
		mReferenceImage = Utils.loadResource(context, R.drawable.f, Highgui.CV_LOAD_IMAGE_COLOR);
		
		if (!mReferenceImage.empty()) {
			
			Mat src_gray  = new Mat();
			Mat threshold_output  = new Mat();
			List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
			
			Imgproc.cvtColor(mReferenceImage, src_gray,Imgproc.COLOR_BGR2GRAY );
			Imgproc.blur(src_gray, src_gray, new Size(3,3));
			Imgproc.threshold(src_gray, threshold_output, 100, 255, Imgproc.THRESH_BINARY);
			Imgproc.findContours(threshold_output, contours, hie,Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
			
			all_letter.add(threshold_output);	
			all_letters.add(contours);
			Log.i(TAG, "Letter : F -- "+mReferenceImage.toString());
		}
		
		mReferenceImage = Utils.loadResource(context, R.drawable.g, Highgui.CV_LOAD_IMAGE_COLOR);
		
		if (!mReferenceImage.empty()) {
			
			Mat src_gray  = new Mat();
			Mat threshold_output  = new Mat();
			List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
			
			Imgproc.cvtColor(mReferenceImage, src_gray,Imgproc.COLOR_BGR2GRAY );
			Imgproc.blur(src_gray, src_gray, new Size(3,3));
			Imgproc.threshold(src_gray, threshold_output, 100, 255, Imgproc.THRESH_BINARY);
			Imgproc.findContours(threshold_output, contours, hie,Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
			
			all_letter.add(threshold_output);	
			all_letters.add(contours);
			Log.i(TAG, "Letter : G -- "+mReferenceImage.toString());
		}
		
		mReferenceImage = Utils.loadResource(context, R.drawable.h, Highgui.CV_LOAD_IMAGE_COLOR);
		
		if (!mReferenceImage.empty()) {
			
			Mat src_gray  = new Mat();
			Mat threshold_output  = new Mat();
			List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
			
			Imgproc.cvtColor(mReferenceImage, src_gray,Imgproc.COLOR_BGR2GRAY );
			Imgproc.blur(src_gray, src_gray, new Size(3,3));
			Imgproc.threshold(src_gray, threshold_output, 100, 255, Imgproc.THRESH_BINARY);
			Imgproc.findContours(threshold_output, contours, hie,Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
			
			all_letter.add(threshold_output);	
			all_letters.add(contours);
			Log.i(TAG, "Letter : H -- "+mReferenceImage.toString());
		}
		
		mReferenceImage = Utils.loadResource(context, R.drawable.i, Highgui.CV_LOAD_IMAGE_COLOR);
		
		if (!mReferenceImage.empty()) {
			
			Mat src_gray  = new Mat();
			Mat threshold_output  = new Mat();
			List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
			
			Imgproc.cvtColor(mReferenceImage, src_gray,Imgproc.COLOR_BGR2GRAY );
			Imgproc.blur(src_gray, src_gray, new Size(3,3));
			Imgproc.threshold(src_gray, threshold_output, 100, 255, Imgproc.THRESH_BINARY);
			Imgproc.findContours(threshold_output, contours, hie,Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
			
			all_letter.add(threshold_output);	
			all_letters.add(contours);
			Log.i(TAG, "Letter : I -- "+mReferenceImage.toString());
		}
		
		mReferenceImage = Utils.loadResource(context, R.drawable.k, Highgui.CV_LOAD_IMAGE_COLOR);
		
		if (!mReferenceImage.empty()) {
			
			Mat src_gray  = new Mat();
			Mat threshold_output  = new Mat();
			List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
			
			Imgproc.cvtColor(mReferenceImage, src_gray,Imgproc.COLOR_BGR2GRAY );
			Imgproc.blur(src_gray, src_gray, new Size(3,3));
			Imgproc.threshold(src_gray, threshold_output, 100, 255, Imgproc.THRESH_BINARY);
			Imgproc.findContours(threshold_output, contours, hie,Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
			
			all_letter.add(threshold_output);	
			all_letters.add(contours);
			Log.i(TAG, "Letter : K -- "+mReferenceImage.toString());
		}
		
		mReferenceImage = Utils.loadResource(context, R.drawable.l, Highgui.CV_LOAD_IMAGE_COLOR);
		
		if (!mReferenceImage.empty()) {
			
			Mat src_gray  = new Mat();
			Mat threshold_output  = new Mat();
			List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
			
			Imgproc.cvtColor(mReferenceImage, src_gray,Imgproc.COLOR_BGR2GRAY );
			Imgproc.blur(src_gray, src_gray, new Size(3,3));
			Imgproc.threshold(src_gray, threshold_output, 100, 255, Imgproc.THRESH_BINARY);
			Imgproc.findContours(threshold_output, contours, hie,Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
			
			all_letter.add(threshold_output);	
			all_letters.add(contours);
			Log.i(TAG, "Letter : l -- "+mReferenceImage.toString());
		}
		
		mReferenceImage = Utils.loadResource(context, R.drawable.m, Highgui.CV_LOAD_IMAGE_COLOR);
		
		if (!mReferenceImage.empty()) {
			
			Mat src_gray  = new Mat();
			Mat threshold_output  = new Mat();
			List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
			
			Imgproc.cvtColor(mReferenceImage, src_gray,Imgproc.COLOR_BGR2GRAY );
			Imgproc.blur(src_gray, src_gray, new Size(3,3));
			Imgproc.threshold(src_gray, threshold_output, 100, 255, Imgproc.THRESH_BINARY);
			Imgproc.findContours(threshold_output, contours, hie,Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
			
			all_letter.add(threshold_output);	
			all_letters.add(contours);
			Log.i(TAG, "Letter : m -- "+mReferenceImage.toString());
		}
		
		
		mReferenceImage = Utils.loadResource(context, R.drawable.n, Highgui.CV_LOAD_IMAGE_COLOR);
		
		if (!mReferenceImage.empty()) {
			
			Mat src_gray  = new Mat();
			Mat threshold_output  = new Mat();
			List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
			
			Imgproc.cvtColor(mReferenceImage, src_gray,Imgproc.COLOR_BGR2GRAY );
			Imgproc.blur(src_gray, src_gray, new Size(3,3));
			Imgproc.threshold(src_gray, threshold_output, 100, 255, Imgproc.THRESH_BINARY);
			Imgproc.findContours(threshold_output, contours, hie,Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
			
			all_letter.add(threshold_output);	
			all_letters.add(contours);
			Log.i(TAG, "Letter : n -- "+mReferenceImage.toString());
		}
		
		mReferenceImage = Utils.loadResource(context, R.drawable.o, Highgui.CV_LOAD_IMAGE_COLOR);
		
		if (!mReferenceImage.empty()) {
			
			Mat src_gray  = new Mat();
			Mat threshold_output  = new Mat();
			List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
			
			Imgproc.cvtColor(mReferenceImage, src_gray,Imgproc.COLOR_BGR2GRAY );
			Imgproc.blur(src_gray, src_gray, new Size(3,3));
			Imgproc.threshold(src_gray, threshold_output, 100, 255, Imgproc.THRESH_BINARY);
			Imgproc.findContours(threshold_output, contours, hie,Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
			
			all_letter.add(threshold_output);	
			all_letters.add(contours);
			Log.i(TAG, "Letter : o -- "+mReferenceImage.toString());
		}
		
		mReferenceImage = Utils.loadResource(context, R.drawable.p, Highgui.CV_LOAD_IMAGE_COLOR);
		
		if (!mReferenceImage.empty()) {
			
			Mat src_gray  = new Mat();
			Mat threshold_output  = new Mat();
			List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
			
			Imgproc.cvtColor(mReferenceImage, src_gray,Imgproc.COLOR_BGR2GRAY );
			Imgproc.blur(src_gray, src_gray, new Size(3,3));
			Imgproc.threshold(src_gray, threshold_output, 100, 255, Imgproc.THRESH_BINARY);
			Imgproc.findContours(threshold_output, contours, hie,Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
			
			all_letter.add(threshold_output);	
			all_letters.add(contours);
			Log.i(TAG, "Letter :  -- "+mReferenceImage.toString());
		}
		
		mReferenceImage = Utils.loadResource(context, R.drawable.q, Highgui.CV_LOAD_IMAGE_COLOR);
		
		if (!mReferenceImage.empty()) {
			
			Mat src_gray  = new Mat();
			Mat threshold_output  = new Mat();
			List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
			
			Imgproc.cvtColor(mReferenceImage, src_gray,Imgproc.COLOR_BGR2GRAY );
			Imgproc.blur(src_gray, src_gray, new Size(3,3));
			Imgproc.threshold(src_gray, threshold_output, 100, 255, Imgproc.THRESH_BINARY);
			Imgproc.findContours(threshold_output, contours, hie,Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
			
			all_letter.add(threshold_output);	
			all_letters.add(contours);
			Log.i(TAG, "Letter : q -- "+mReferenceImage.toString());
		}
		
		mReferenceImage = Utils.loadResource(context, R.drawable.r, Highgui.CV_LOAD_IMAGE_COLOR);
		
		if (!mReferenceImage.empty()) {
			
			Mat src_gray  = new Mat();
			Mat threshold_output  = new Mat();
			List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
			
			Imgproc.cvtColor(mReferenceImage, src_gray,Imgproc.COLOR_BGR2GRAY );
			Imgproc.blur(src_gray, src_gray, new Size(3,3));
			Imgproc.threshold(src_gray, threshold_output, 100, 255, Imgproc.THRESH_BINARY);
			Imgproc.findContours(threshold_output, contours, hie,Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
			
			all_letter.add(threshold_output);	
			all_letters.add(contours);
			Log.i(TAG, "Letter : r -- "+mReferenceImage.toString());
		}
		
		mReferenceImage = Utils.loadResource(context, R.drawable.s, Highgui.CV_LOAD_IMAGE_COLOR);
		
		if (!mReferenceImage.empty()) {
			
			Mat src_gray  = new Mat();
			Mat threshold_output  = new Mat();
			List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
			
			Imgproc.cvtColor(mReferenceImage, src_gray,Imgproc.COLOR_BGR2GRAY );
			Imgproc.blur(src_gray, src_gray, new Size(3,3));
			Imgproc.threshold(src_gray, threshold_output, 100, 255, Imgproc.THRESH_BINARY);
			Imgproc.findContours(threshold_output, contours, hie,Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
			
			all_letter.add(threshold_output);	
			all_letters.add(contours);
			Log.i(TAG, "Letter : s -- "+mReferenceImage.toString());
		}
		
		mReferenceImage = Utils.loadResource(context, R.drawable.t, Highgui.CV_LOAD_IMAGE_COLOR);
		
		if (!mReferenceImage.empty()) {
			
			Mat src_gray  = new Mat();
			Mat threshold_output  = new Mat();
			List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
			
			Imgproc.cvtColor(mReferenceImage, src_gray,Imgproc.COLOR_BGR2GRAY );
			Imgproc.blur(src_gray, src_gray, new Size(3,3));
			Imgproc.threshold(src_gray, threshold_output, 100, 255, Imgproc.THRESH_BINARY);
			Imgproc.findContours(threshold_output, contours, hie,Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
			
			all_letter.add(threshold_output);	
			all_letters.add(contours);
			Log.i(TAG, "Letter : t -- "+mReferenceImage.toString());
		}
		
		mReferenceImage = Utils.loadResource(context, R.drawable.u, Highgui.CV_LOAD_IMAGE_COLOR);
		
		if (!mReferenceImage.empty()) {
			
			Mat src_gray  = new Mat();
			Mat threshold_output  = new Mat();
			List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
			
			Imgproc.cvtColor(mReferenceImage, src_gray,Imgproc.COLOR_BGR2GRAY );
			Imgproc.blur(src_gray, src_gray, new Size(3,3));
			Imgproc.threshold(src_gray, threshold_output, 100, 255, Imgproc.THRESH_BINARY);
			Imgproc.findContours(threshold_output, contours, hie,Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
			
			all_letter.add(threshold_output);	
			all_letters.add(contours);
			Log.i(TAG, "Letter : u -- "+mReferenceImage.toString());
		}
		
		mReferenceImage = Utils.loadResource(context, R.drawable.v, Highgui.CV_LOAD_IMAGE_COLOR);
		
		if (!mReferenceImage.empty()) {
			
			Mat src_gray  = new Mat();
			Mat threshold_output  = new Mat();
			List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
			
			Imgproc.cvtColor(mReferenceImage, src_gray,Imgproc.COLOR_BGR2GRAY );
			Imgproc.blur(src_gray, src_gray, new Size(3,3));
			Imgproc.threshold(src_gray, threshold_output, 100, 255, Imgproc.THRESH_BINARY);
			Imgproc.findContours(threshold_output, contours, hie,Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
			
			all_letter.add(threshold_output);	
			all_letters.add(contours);
			Log.i(TAG, "Letter : v -- "+mReferenceImage.toString());
		}
		
		mReferenceImage = Utils.loadResource(context, R.drawable.w, Highgui.CV_LOAD_IMAGE_COLOR);
		
		if (!mReferenceImage.empty()) {
			
			Mat src_gray  = new Mat();
			Mat threshold_output  = new Mat();
			List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
			
			Imgproc.cvtColor(mReferenceImage, src_gray,Imgproc.COLOR_BGR2GRAY );
			Imgproc.blur(src_gray, src_gray, new Size(3,3));
			Imgproc.threshold(src_gray, threshold_output, 100, 255, Imgproc.THRESH_BINARY);
			Imgproc.findContours(threshold_output, contours, hie,Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
			
			all_letter.add(threshold_output);	
			all_letters.add(contours);
			Log.i(TAG, "Letter : w -- "+mReferenceImage.toString());
		}
		
		mReferenceImage = Utils.loadResource(context, R.drawable.x, Highgui.CV_LOAD_IMAGE_COLOR);
		
		if (!mReferenceImage.empty()) {
			
			Mat src_gray  = new Mat();
			Mat threshold_output  = new Mat();
			List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
			
			Imgproc.cvtColor(mReferenceImage, src_gray,Imgproc.COLOR_BGR2GRAY );
			Imgproc.blur(src_gray, src_gray, new Size(3,3));
			Imgproc.threshold(src_gray, threshold_output, 100, 255, Imgproc.THRESH_BINARY);
			Imgproc.findContours(threshold_output, contours, hie,Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
			
			all_letter.add(threshold_output);
			all_letters.add(contours);
			Log.i(TAG, "Letter : x -- "+mReferenceImage.toString());
		}
		
		mReferenceImage = Utils.loadResource(context, R.drawable.y, Highgui.CV_LOAD_IMAGE_COLOR);
		
		if (!mReferenceImage.empty()) {
			
			Mat src_gray  = new Mat();
			Mat threshold_output  = new Mat();
			List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
			
			Imgproc.cvtColor(mReferenceImage, src_gray,Imgproc.COLOR_BGR2GRAY );
			Imgproc.blur(src_gray, src_gray, new Size(3,3));
			Imgproc.threshold(src_gray, threshold_output, 100, 255, Imgproc.THRESH_BINARY);
			Imgproc.findContours(threshold_output, contours, hie,Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
			
			all_letter.add(threshold_output);
			all_letters.add(contours);
			Log.i(TAG, "Letter : y -- "+mReferenceImage.toString());
		}

		mReferenceImage = Utils.loadResource(context, R.drawable.ilu, Highgui.CV_LOAD_IMAGE_COLOR);
		
		if (!mReferenceImage.empty()) {
			
			Mat src_gray  = new Mat();
			Mat threshold_output  = new Mat();
			List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
			
			Imgproc.cvtColor(mReferenceImage, src_gray,Imgproc.COLOR_BGR2GRAY );
			Imgproc.blur(src_gray, src_gray, new Size(3,3));
			Imgproc.threshold(src_gray, threshold_output, 100, 255, Imgproc.THRESH_BINARY);
			Imgproc.findContours(threshold_output, contours, hie,Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
			
			all_letter.add(threshold_output);
			all_letters.add(contours);
			Log.i(TAG, "Letter : I LOVE U -- "+mReferenceImage.toString());
		}
		Log.i(TAG, "Number of letter : -- "+all_letter.size());
	}	
	
	String compare(Mat img){
		
		Mat src_gray  = new Mat();
		Mat threshold_output  = new Mat();
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		
		Imgproc.cvtColor(img, src_gray,Imgproc.COLOR_BGR2GRAY );
		Imgproc.blur(src_gray, src_gray, new Size(3,3));
		Imgproc.threshold(src_gray, threshold_output, 100, 255, Imgproc.THRESH_BINARY);
		
//		Imgproc.cvtColor(img, img, Imgproc.COLOR_RGBA2GRAY);
		double hi_val=0;
		int best=0;
		for (int i = 0; i < all_letter.size(); i++) {
			if (all_letter.get(i)!= null) {
				double val=Core.norm(threshold_output, all_letter.get(i));
				
				if (hi_val < val) {
					hi_val = val;
					best =  i;
				}
			}
			
		}
		
		return letters[best];
		
//		String hasil=null;
//		Double lowestDiff=Double.MAX_VALUE;
//		int best=0;
//					
//		for (int i = 0; i < all_letters.size(); i++) {
//			if (!all_letters.get(i).isEmpty()) {
//				
////					findBiggestContourLetter(all_letters.get(i));
////					
////					Mat letter=all_letters.get(i).get(lMaxId);
////					Mat contur=contours.get(cMaxId);
////					
////					// Match Shapes functions (possible alternative)
////					double diff = Imgproc.matchShapes(letter, contur, Imgproc.CV_CONTOURS_MATCH_I3, 0);
////					diff += Imgproc.matchShapes(letter, contur, Imgproc.CV_CONTOURS_MATCH_I2, 0);
////					diff += Imgproc.matchShapes(letter, contur, Imgproc.CV_CONTOURS_MATCH_I1, 0);
//
//				double diff = distance_hausdorff(all_letters.get(i),contours);
//				
//
//				if (diff < lowestDiff) {
//					lowestDiff = diff;
//					best =  i;
//				}
//			}
//		}
//		return hasil=letters[best];
				
	}
	
	// Source: http://stackoverflow.com/questions/21482534/how-to-use-shape-distance-and-common-interfaces-to-find-hausdorff-distance-in-op
	double distance_2(Point[] a, Point[] b) {
			
			//find distanace
			int maxDistAB = 0;
			for (int i = 0; i < a.length; i++) {
				double minB = 1000000;
				for (int j = 0; j < b.length; j++) {
					double dx =  (a[i].x - b[j].x);
					double dy = (a[i].y - b[j].y);
					double tmpDist = dx*dx + dy*dy;

					if (tmpDist < minB) {
						minB = tmpDist;
					}
					if (tmpDist == 0) {
						break; // can't get better than equal.
					}
				}
				maxDistAB += minB;
			}
			return maxDistAB;
		}
		
		double distance_hausdorff(List<MatOfPoint> a, List<MatOfPoint> b) {
			
			findBiggestContourLetter(a);
					
			Point[] contourPts = a.get(lMaxId).toArray();
			Point[] contourPtsL = b.get(cMaxId).toArray();
							
			double maxDistAB = distance_2(contourPts, contourPtsL);
			double maxDistBA = distance_2(contourPtsL, contourPts);
//			double maxDist = max(maxDistAB,maxDistBA);
			
			double maxDist=(maxDistAB < maxDistBA)?	maxDistBA:maxDistBA;   
				
			return Math.sqrt((double)maxDist);
		}
	
	void findBiggestContour() 
	{
		int idx = -1;
		int cNum = 0;
		
		for (int i = 0; i < contours.size(); i++)
		{
			int curNum = contours.get(i).toList().size();
			if (curNum > cNum) {
				idx = i;
				cNum = curNum;
			}
		}		
		cMaxId = idx;
	}
	
	void findBiggestContourLetter(List<MatOfPoint> contorLetter) 
	{
		int idx = -1;
		int cNum = 0;
		
		for (int i = 0; i < contorLetter.size(); i++)
		{
			int curNum = contorLetter.get(i).toList().size();
			if (curNum > cNum) {
				idx = i;
				cNum = curNum;
			}
		}
		
		lMaxId = idx;
	}
	
}
