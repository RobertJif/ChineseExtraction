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
import sun.rmi.runtime.Log;
/**
 *
 * @author Robert
 */
public class TrueTemplateMatching {
        static double r = 0;
        static double x = 0;
        static double y = 0;
        static double ym = 0;
        static double xm = 0;
        static int N = 200;
        static int M = 200;
        static int jumlahpx = N*M;
    public static void main(String[] args) {
        
        
//        System.out.println("X mean = "+avgpixelx);
//        System.out.println("Y mean = "+avgpixely);
        
        
    }
    public double hitungKorelasi(Mat a, Mat b){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        Mat fileX = new Mat();
        fileX = a;
        
        Mat fileY = new Mat();
        fileY = b;
        
        Size ukuran = new Size(N,M);
        
        Imgproc.resize(fileY, fileY, ukuran);
        Imgproc.resize(fileX, fileX, ukuran);
        
        int sumx = fileX.rows() * fileX.cols();
        int sumy = fileY.rows() * fileY.cols();
        
        xm = meanpixel(fileX,sumx);
        ym = meanpixel(fileY,sumy);
        
        
        double nilair = TemplateMatch(fileX, fileY);
        return nilair;
    }
    public double TemplateMatch(Mat x,Mat y){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        double xtemp = 0;
        double ytemp = 0;
        
        double tempatas = 0;
        double tempbawah = 0;
        Mat xbaru = Mat.zeros(N,M, CvType.CV_8UC1);
        Mat ybaru = Mat.zeros(N,M, CvType.CV_8UC1);
        Mat atas = Mat.zeros(N,M, CvType.CV_8UC1);
        Mat bawah = Mat.zeros(N,M, CvType.CV_8UC1);
        
        double xtemp2 = 0;
        double ytemp2 = 0;
        
        Mat xbaru2 = Mat.zeros(N,M, CvType.CV_8UC1);
        Mat ybaru2 = Mat.zeros(N,M, CvType.CV_8UC1);
        
        Mat rmat = Mat.zeros(N,M, CvType.CV_8UC1);
        
        
        double tempxx[] = new double[1];
        double tempyy[] = new double[1];
        
        for(int i = 0;i< N;i++){
            for(int j = 0;j<M; j++){
                
                tempxx = x.get(i, j);
                xtemp = (int)tempxx[0];
                //System.out.println("xtemp = "+xtemp);
                tempyy = y.get(i, j);
                ytemp = (int)tempyy[0];
                //System.out.println("ytemp = "+ytemp);
                
                xtemp = xtemp - xm;
                //xtemp = Math.abs(xtemp);
                //System.out.println("xtemp setelah kurang = "+xtemp);
                
                ytemp = ytemp - ym;
                //ytemp = Math.abs(ytemp);
                //System.out.println("ytemp setelah kurang = "+ytemp);
                
                tempatas = xtemp * ytemp;
                
                
                tempbawah = (double) (Math.sqrt(Math.pow(xtemp, 2)) * Math.sqrt(Math.pow(ytemp, 2)));
                
                //System.out.println("tempatas = "+tempatas );
               // System.out.println("tempbawah = "+tempbawah);
                //int rint = Math.abs(tempatas / tempbawah);
                //int rint = 0;
                //rmat.put(i, j, rint);
                atas.put(i, j, tempatas);
                bawah.put(i, j, tempbawah);
            }
            //System.out.println("--------------------------------------------------------");
        }
        Mat rmatakhir = Mat.zeros(N,M, CvType.CV_8UC1);
        Core.divide(atas, bawah, rmatakhir);
        r = meanpixel(rmatakhir,jumlahpx);
        return r;
    }
    
    public double meanpixel(Mat input,int jumlahpixel){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        double hasil[] = new double[1];
        int jumlah = 0;
        int posisi = 0;
        for(int i=0;i< input.rows();i++){
            for(int j=0;j<input.cols();j++){
                hasil = input.get(i, j);
                jumlah = jumlah + (int)hasil[0];
                  
            }
        }
        //System.out.println("Hasil "+jumlah +" dibagi "+jumlahpixel);
        double hasilmean = (double) jumlah/jumlahpixel;
        return hasilmean;
        
    }
    
}
