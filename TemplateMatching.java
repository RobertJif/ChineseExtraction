/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chineseextraction;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.videoio.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.TM_CCOEFF_NORMED;
import sun.rmi.runtime.Log;

/**0
 *
 * @author Robert
 */
public class TemplateMatching {
    static int gores2 = 0;
//    public static void main(String[] args) {
//      System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
//    Mat inputan = new Mat();
//    inputan = Imgcodecs.imread(".jpg");
//        hitungGoresan(inputan);
//        System.out.println(gores2);
//    }
    
    public int hitungGoresan(Mat input){
        File f = new File("C:\\Users\\Robert\\Documents\\NetBeansProjects\\ChineseExtraction\\templategoresan");
        File[] list = f.listFiles();
        Mat goresan = new Mat();
        int jumlah = 0;
        StrokeExtraction x = new StrokeExtraction();
        boolean kebenaran;
        for(int i = 0; i<list.length;i++){
            goresan = Imgcodecs.imread(list[i].getAbsolutePath());
            Size ukuran = new Size(100, 100);
            Imgproc.resize(goresan, goresan,ukuran );
            Imgproc.resize(input, input,ukuran );
            
           // goresan = x.ambiltulang(goresan);
//            try{
//                
//            matchTemplate(input,goresan , 2);
//            jumlah = jumlah + 1;
//            }catch(Exception a){
//                jumlah =jumlah -1;
//            }
            templateMatch2(goresan, input);
        }
      //  System.out.println("Goresan Terdeteksi = "+gores2 );
         int goresanbulat;
         if(gores2>=7){
            goresanbulat = 7;
        }else if(gores2<=3){
            goresanbulat = 3;
        }else{
            goresanbulat = 5;
        }
         gores2 = 0;
            return goresanbulat;
        
    }
//    public static void matchTemplate(Mat img, Mat templ,int match_method) {
//        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
//       
//
//        int result_cols = img.cols() - templ.cols() + 1;
//        int result_rows = img.rows() - templ.rows() + 1;
//        Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);
//
//    //  Bitmap inFileBitmap = BitmapFactory.decodeFile(inFile);
//    //  Bitmap templBitmap = BitmapFactory.decodeFile(templateFile);
//        // / Do the Matching and Normalize
//        //Utils.bitmapToMat(inFileBitmap, img);
//        //Utils.bitmapToMat(templBitmap, templ);
//        Imgproc.matchTemplate(img, templ, result, match_method);
//        //Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());
//
//        // / Localizing the best match with minMaxLoc
//        MinMaxLocResult mmr = Core.minMaxLoc(result);
//
//        Point matchLoc;
//        double minVal; double maxVal;
//        if (match_method == Imgproc.TM_SQDIFF
//                || match_method == Imgproc.TM_SQDIFF_NORMED) {
//            matchLoc = mmr.minLoc;
//        } else {
//            matchLoc = mmr.maxLoc;
//        }
//        // / Show me what you got
//        Imgproc.rectangle(img, matchLoc, new Point(matchLoc.x + templ.cols(),matchLoc.y + templ.rows()), new Scalar(0, 0,0));
//        
//
//    // Save the visualized detection.
//
//}    
    public static void templateMatch2(Mat img, Mat templ){
          System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
       

        int result_cols = img.cols() - templ.cols() + 1;
        int result_rows = img.rows() - templ.rows() + 1;
        Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);

        Imgproc.matchTemplate(img, templ, result, TM_CCOEFF_NORMED);
        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
        double minMatchQuality = 0.03 ;
       // System.out.println(minMatchQuality);
// with CV_TM_SQDIFF_NORMED you could use 0.1
        //System.out.println(mmr.minVal);
        //System.out.println(mmr.maxVal);
        
        //System.out.println("");
        if (mmr.maxVal > minMatchQuality){ // with CV_TM_SQDIFF_NORMED use minValue < minMatchQuality
            gores2 = gores2+1;
        }else{
//            if(gores2<1){
//                gores2 = 0;
//            }
        }
}
}