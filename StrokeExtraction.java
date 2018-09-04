/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chineseextraction;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Robert
 */
public class StrokeExtraction {
    public Mat ambiltulang(Mat input){
    System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
    Mat temp = new Mat();
    Imgproc.threshold(input, input, 127, 255, Imgproc.THRESH_BINARY);
    Mat skel = new Mat(input.size(),CvType.CV_8UC1);   
    Mat eroded = new Mat();
    Size ukuran = new Size(3,3);
    
    Mat element = new Mat();
    element = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS,ukuran );
    boolean done = false;
    do{
       Imgproc.erode(input, eroded, element);
       Imgproc.dilate(eroded, temp, element);
       Core.subtract(input, temp, temp);
       Core.bitwise_or(skel, temp, skel);
       eroded.copyTo(input);
       done = (Core.countNonZero(input)==0);
    }while(!done);
    return skel;
    }
    
    
}
