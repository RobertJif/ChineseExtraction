/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chineseextraction;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import java.util.Arrays;
import org.opencv.imgcodecs.Imgcodecs;
/**
 *
 * @author Robert
 */
public class reader5 {
    static String hasilapp[] = new String[2];
    static String tempkata = "";
    static String hurufbesar ="";
    static String temphuruf = "";
    String allkr5 = "";
    static double tetinggi = 0;
    static File f = new File("C:\\Users\\Robert\\Documents\\NetBeansProjects\\ChineseExtraction\\TrueTraining\\TrainingGoresan5\\");
    static File[] list = f.listFiles();
    static boolean match = false;
    static String[] namahuruf = new String[list.length];      
    static String[] namafilenya = new String[list.length];      
    
    static double[] nilaikorelasinya = new double[list.length];
    static String namahurufnya = "";
     
    public static void main(String[] args) throws SQLException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        
//        Mat inidia = Imgcodecs.imread("input.jpg");
//       
//        ambildata(olahsemua(olahtemplate(inidia)));
//        
    }
    public static int getFiles(){
        return list.length;
    }
    public String olahtemplate(Mat input,int gores) throws SQLException{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
         TrueTemplateMatching go = new TrueTemplateMatching();
         Mat temp = new Mat();
         int [] urutan = new int[list.length];
         for(int t = 0;t<nilaikorelasinya.length;t++){
             temp = Imgcodecs.imread(list[t].getAbsolutePath());
             nilaikorelasinya[t] = go.hitungKorelasi(input, temp);
             urutan[t] = t;
            // System.out.println("Index "+urutan[t]+" = "+nilaikorelasinya[t]);
             
         }
         double simpan = 0;
         int nomor = 0;
//         for(int s = 0;s<urutan.length-1;s++){
//             
//             for(int d=s;d<urutan.length-s;d++){
//                 if(nilaikorelasinya[d]<nilaikorelasinya[d+1]){
//                     simpan = nilaikorelasinya[d];
//                     nilaikorelasinya[d] = nilaikorelasinya[d+1];
//                     nilaikorelasinya[d+1]= simpan;
//                     nomor = urutan[d];
//                     urutan[d] = urutan[d+1];
//                     urutan[d+1] = nomor;
//                 }
//             }
//         }
         int n = nilaikorelasinya.length;
        for(int i=0; i < n; i++){  
                 for(int j=1; j < (n-i); j++){  
                          if(nilaikorelasinya[j-1] > nilaikorelasinya[j]){  
                                 //swap elements  
                                 simpan = nilaikorelasinya[j-1];  
                                 nilaikorelasinya[j-1] = nilaikorelasinya[j];  
                                 nilaikorelasinya[j] = simpan;
                                 nomor = urutan[j-1];  
                                 urutan[j-1] = urutan[j];  
                                 urutan[j] = nomor;  
                         }  
                          
                 }  }
         //System.out.println("Terbesar : "+nilaikorelasinya[(urutan.length-1)]+" Index = "+urutan[urutan.length-1]);
       for(int ii = 0;ii<nilaikorelasinya.length;ii++){
//             System.out.println(list[urutan[ii]].getName());
      //       System.out.println(nilaikorelasinya[ii]);
//             System.out.println("");
             setAllkorelasi5(list[urutan[ii]].getName(), nilaikorelasinya[ii]);
             
         }
    //  System.out.println(nilaikorelasinya[nilaikorelasinya.length-1]);
         sethigh(nilaikorelasinya[urutan.length-1]);     
    return ambildata(olahsemua(urutan[(urutan.length-1)]),gores);
    }
     public void sethigh(double tetinggi){
        this.tetinggi = tetinggi;
    }
    public double gethigh(){
        return tetinggi;
    }
    public void setAllkorelasi5(String input,double input2){
        allkr5 = allkr5 + input + " = " + input2 + "\n";
        
    }
    public String getAllkorelasi5(){
        return allkr5;
    }
     public static String olahsemua(int filenyaa) throws SQLException{
         System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
         
             String inidiaaaa  =  "";
             tempkata = list[filenyaa].getName();
            
            for(int j=0; j<tempkata.length();j++){
                temphuruf = tempkata.charAt(j)+"";
                if(temphuruf.equals(".")){
                    namahurufnya = tempkata.substring(0, j);
                     inidiaaaa = namahurufnya;
                    
                    //System.out.println(namahuruf[i]);
                }
            }
            //System.out.println(namahuruf[i]);
         //   System.out.println(inidiaaaa);
       
        return inidiaaaa;
     }
        
        //namahurufnya = "zhe1";
        //System.out.println(namahurufnya);
        
        
     
    public static String ambildata(String kode,int gores) throws SQLException{
  
        try{
        
        Connection conn = ConnectDb();
          //  System.out.println("ambildata goresan = "+gores);
        //PreparedStatement sta = conn.prepareStatement("SELECT huruf FROM template where template.nama_huruf='"+kode+"' and template.jumlahgoresan="+goresan);
        //PreparedStatement sta = conn.prepareStatement("SELECT huruf,nama_huruf,arti FROM template where jumlahgoresan="+gores);
        PreparedStatement sta = conn.prepareStatement("SELECT huruf,nama_huruf,arti FROM template");
        
        ResultSet rs = sta.executeQuery();
        String str = "";
        while(rs.next()){
        str = rs.getString("nama_huruf");
            //System.out.println(str);
        if(kode.contains(str)){
            hasilapp[0] = rs.getString("nama_huruf");
            hurufbesar = rs.getString("arti");
            hasilapp[1] = Character.toUpperCase(hurufbesar.charAt(0)) + hurufbesar.substring(1);
           
         //   System.out.println(hasilapp[0]);
            
        }
        }
        }catch(Exception e){
            System.out.println("Gagal");
        }
        return hasilapp[0];
    }
    public String getArti(){
        return hasilapp[1];
    }public String getArti2(){
        return hasilapp[0];
    }
    public static Connection ConnectDb(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/mandarin", "root","");
            //System.out.println("Connected");
            return conn;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
    
    }
