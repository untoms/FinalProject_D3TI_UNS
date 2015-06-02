#include <jni.h>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>

#include <vector>

#include <opencv2/contrib/detection_based_tracker.hpp>

#include <string>

#include <android/log.h>

#define LOG_TAG "FaceDetection/DetectionBasedTracker"
#define LOGD(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__))

using namespace std;
using namespace cv;

extern "C" {
JNIEXPORT jdouble JNICALL Java_id_bustomi_d3ti_uns_readmysign2_HandGesture_findInscribedCircleJNI(JNIEnv* env, jobject obj, jlong imgAddr,
		jdouble rectTLX, jdouble rectTLY, jdouble rectBRX, jdouble rectBRY,
		jdoubleArray incircleX, jdoubleArray incircleY, jlong contourAddr);

/*
 * Class:     org_opencv_samples_fd_DetectionBasedTracker
 * Method:    nativeCreateObject
 * Signature: (Ljava/lang/String;F)J
 */
JNIEXPORT jlong JNICALL Java_id_bustomi_d3ti_uns_readmysign2_DetectionBasedTracker_nativeCreateObject
  (JNIEnv *, jclass, jstring, jint);

/*
 * Class:     org_opencv_samples_fd_DetectionBasedTracker
 * Method:    nativeDestroyObject
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_id_bustomi_d3ti_uns_readmysign2_DetectionBasedTracker_nativeDestroyObject
  (JNIEnv *, jclass, jlong);

/*
 * Class:     org_opencv_samples_fd_DetectionBasedTracker
 * Method:    nativeStart
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_id_bustomi_d3ti_uns_readmysign2_DetectionBasedTracker_nativeStart
  (JNIEnv *, jclass, jlong);

/*
 * Class:     org_opencv_samples_fd_DetectionBasedTracker
 * Method:    nativeStop
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_id_bustomi_d3ti_uns_readmysign2_DetectionBasedTracker_nativeStop
  (JNIEnv *, jclass, jlong);

  /*
   * Class:     org_opencv_samples_fd_DetectionBasedTracker
   * Method:    nativeSetFaceSize
   * Signature: (JI)V
   */
  JNIEXPORT void JNICALL Java_id_bustomi_d3ti_uns_readmysign2_DetectionBasedTracker_nativeSetFaceSize
  (JNIEnv *, jclass, jlong, jint);

/*
 * Class:     org_opencv_samples_fd_DetectionBasedTracker
 * Method:    nativeDetect
 * Signature: (JJJ)V
 */
JNIEXPORT void JNICALL Java_id_bustomi_d3ti_uns_readmysign2_DetectionBasedTracker_nativeDetect
  (JNIEnv *, jclass, jlong, jlong, jlong);

JNIEXPORT jdouble JNICALL Java_id_bustomi_d3ti_uns_readmysign2_HandGesture_findInscribedCircleJNI(JNIEnv* env, jobject obj, jlong imgAddr,
		jdouble rectTLX, jdouble rectTLY, jdouble rectBRX, jdouble rectBRY,
		jdoubleArray incircleX, jdoubleArray incircleY, jlong contourAddr)
{
	Mat& img_cpp  = *(Mat*)imgAddr;

	//vector<Point2f>& contour = *(vector<Point2f> *)contourAddr;
	Mat& contourMat = *(Mat*)contourAddr;
	vector<Point2f> contourVec;
	contourMat.copyTo(contourVec);

	double r = 0;
	double targetX = 0;
	double targetY = 0;

	for (int y = (int)rectTLY; y < (int)rectBRY; y++)
	{
		for (int x = (int)rectTLX; x < (int)rectBRX; x++)
		{
			double curDist = pointPolygonTest(contourVec, Point2f(x, y), true);

			if (curDist > r) {
				r = curDist;
				targetX = x;
				targetY = y;
			}
		}
	}

	jdouble outArrayX[] = {0};
	jdouble outArrayY[] = {0};

	outArrayX[0] = targetX;
	outArrayY[0] = targetY;

	env->SetDoubleArrayRegion(incircleX, 0 , 1, (const jdouble*)outArrayX);
	env->SetDoubleArrayRegion(incircleY, 0 , 1, (const jdouble*)outArrayY);
	//Core.circle(img, inCircle, (int)inCircleRadius, new Scalar(240,240,45,0), 2);


	return r;
}

inline void vector_Rect_to_Mat(vector<Rect>& v_rect, Mat& mat)
{
    mat = Mat(v_rect, true);
}

JNIEXPORT jlong JNICALL Java_id_bustomi_d3ti_uns_readmysign2_DetectionBasedTracker_nativeCreateObject
(JNIEnv * jenv, jclass, jstring jFileName, jint faceSize)
{
    LOGD("Java_id_bustomi_d3ti_uns_readmysign2_DetectionBasedTracker_nativeCreateObject enter");
    const char* jnamestr = jenv->GetStringUTFChars(jFileName, NULL);
    string stdFileName(jnamestr);
    jlong result = 0;

    try
    {
        DetectionBasedTracker::Parameters DetectorParams;
        if (faceSize > 0)
            DetectorParams.minObjectSize = faceSize;
        result = (jlong)new DetectionBasedTracker(stdFileName, DetectorParams);
    }
    catch(cv::Exception& e)
    {
        LOGD("nativeCreateObject caught cv::Exception: %s", e.what());
        jclass je = jenv->FindClass("org/opencv/core/CvException");
        if(!je)
            je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, e.what());
    }
    catch (...)
    {
        LOGD("nativeCreateObject caught unknown exception");
        jclass je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, "Unknown exception in JNI code of DetectionBasedTracker.nativeCreateObject()");
        return 0;
    }

    LOGD("Java_id_bustomi_d3ti_uns_readmysign2_DetectionBasedTracker_nativeCreateObject exit");
    return result;
}

JNIEXPORT void JNICALL Java_id_bustomi_d3ti_uns_readmysign2_DetectionBasedTracker_nativeDestroyObject
(JNIEnv * jenv, jclass, jlong thiz)
{
    LOGD("Java_id_bustomi_d3ti_uns_readmysign2_DetectionBasedTracker_nativeDestroyObject enter");
    try
    {
        if(thiz != 0)
        {
            ((DetectionBasedTracker*)thiz)->stop();
            delete (DetectionBasedTracker*)thiz;
        }
    }
    catch(cv::Exception& e)
    {
        LOGD("nativeestroyObject caught cv::Exception: %s", e.what());
        jclass je = jenv->FindClass("org/opencv/core/CvException");
        if(!je)
            je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, e.what());
    }
    catch (...)
    {
        LOGD("nativeDestroyObject caught unknown exception");
        jclass je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, "Unknown exception in JNI code of DetectionBasedTracker.nativeDestroyObject()");
    }
    LOGD("Java_id_bustomi_d3ti_uns_readmysign2_DetectionBasedTracker_nativeDestroyObject exit");
}

JNIEXPORT void JNICALL Java_id_bustomi_d3ti_uns_readmysign2_DetectionBasedTracker_nativeStart
(JNIEnv * jenv, jclass, jlong thiz)
{
    LOGD("Java_id_bustomi_d3ti_uns_readmysign2_DetectionBasedTracker_nativeStart enter");
    try
    {
        ((DetectionBasedTracker*)thiz)->run();
    }
    catch(cv::Exception& e)
    {
        LOGD("nativeStart caught cv::Exception: %s", e.what());
        jclass je = jenv->FindClass("org/opencv/core/CvException");
        if(!je)
            je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, e.what());
    }
    catch (...)
    {
        LOGD("nativeStart caught unknown exception");
        jclass je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, "Unknown exception in JNI code of DetectionBasedTracker.nativeStart()");
    }
    LOGD("Java_id_bustomi_d3ti_uns_readmysign2_DetectionBasedTracker_nativeStart exit");
}

JNIEXPORT void JNICALL Java_id_bustomi_d3ti_uns_readmysign2_DetectionBasedTracker_nativeStop
(JNIEnv * jenv, jclass, jlong thiz)
{
    LOGD("Java_id_bustomi_d3ti_uns_readmysign2_DetectionBasedTracker_nativeStop enter");
    try
    {
        ((DetectionBasedTracker*)thiz)->stop();
    }
    catch(cv::Exception& e)
    {
        LOGD("nativeStop caught cv::Exception: %s", e.what());
        jclass je = jenv->FindClass("org/opencv/core/CvException");
        if(!je)
            je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, e.what());
    }
    catch (...)
    {
        LOGD("nativeStop caught unknown exception");
        jclass je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, "Unknown exception in JNI code of DetectionBasedTracker.nativeStop()");
    }
    LOGD("Java_id_bustomi_d3ti_uns_readmysign2_DetectionBasedTracker_nativeStop exit");
}

JNIEXPORT void JNICALL Java_id_bustomi_d3ti_uns_readmysign2_DetectionBasedTracker_nativeSetFaceSize
(JNIEnv * jenv, jclass, jlong thiz, jint faceSize)
{
    LOGD("Java_id_bustomi_d3ti_uns_readmysign2_DetectionBasedTracker_nativeSetFaceSize enter");
    try
    {
        if (faceSize > 0)
        {
            DetectionBasedTracker::Parameters DetectorParams = \
            ((DetectionBasedTracker*)thiz)->getParameters();
            DetectorParams.minObjectSize = faceSize;
            ((DetectionBasedTracker*)thiz)->setParameters(DetectorParams);
        }
    }
    catch(cv::Exception& e)
    {
        LOGD("nativeStop caught cv::Exception: %s", e.what());
        jclass je = jenv->FindClass("org/opencv/core/CvException");
        if(!je)
            je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, e.what());
    }
    catch (...)
    {
        LOGD("nativeSetFaceSize caught unknown exception");
        jclass je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, "Unknown exception in JNI code of DetectionBasedTracker.nativeSetFaceSize()");
    }
    LOGD("Java_id_bustomi_d3ti_uns_readmysign2_DetectionBasedTracker_nativeSetFaceSize exit");
}


JNIEXPORT void JNICALL Java_id_bustomi_d3ti_uns_readmysign2_DetectionBasedTracker_nativeDetect
(JNIEnv * jenv, jclass, jlong thiz, jlong imageGray, jlong faces)
{
    LOGD("Java_id_bustomi_d3ti_uns_readmysign2_DetectionBasedTracker_nativeDetect enter");
    try
    {
        vector<Rect> RectFaces;
        ((DetectionBasedTracker*)thiz)->process(*((Mat*)imageGray));
        ((DetectionBasedTracker*)thiz)->getObjects(RectFaces);
        vector_Rect_to_Mat(RectFaces, *((Mat*)faces));
    }
    catch(cv::Exception& e)
    {
        LOGD("nativeCreateObject caught cv::Exception: %s", e.what());
        jclass je = jenv->FindClass("org/opencv/core/CvException");
        if(!je)
            je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, e.what());
    }
    catch (...)
    {
        LOGD("nativeDetect caught unknown exception");
        jclass je = jenv->FindClass("java/lang/Exception");
        jenv->ThrowNew(je, "Unknown exception in JNI code DetectionBasedTracker.nativeDetect()");
    }
    LOGD("Java_id_bustomi_d3ti_uns_readmysign2_DetectionBasedTracker_nativeDetect exit");
}


}
