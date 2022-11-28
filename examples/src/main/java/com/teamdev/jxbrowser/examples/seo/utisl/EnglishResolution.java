package com.teamdev.jxbrowser.examples.seo.utisl;


import com.teamdev.jxbrowser.examples.seo.ua.L;
import java.util.ArrayList;
import java.util.List;

public class EnglishResolution {


  private  static List<WidthHeiht> pcWidthHeight=null;
  private  static List<WidthHeiht> mobileWidthHeight=null;
  static {
      //pc 屏幕分辨率
      pcWidthHeight= new ArrayList<>();
      pcWidthHeight.add(new WidthHeiht(1280,800));
      pcWidthHeight.add(new WidthHeiht(1920,1200));
      pcWidthHeight.add(new WidthHeiht(1440,900));
      pcWidthHeight.add(new WidthHeiht(1680,1050));
      pcWidthHeight.add(new WidthHeiht(1920,1080));
      pcWidthHeight.add(new WidthHeiht(1280,1024));
      pcWidthHeight.add(new WidthHeiht(1280,720));
      pcWidthHeight.add(new WidthHeiht(2560,1440));
      pcWidthHeight.add(new WidthHeiht(1366,768));
      pcWidthHeight.add(new WidthHeiht(1600,1200));


      pcWidthHeight.add(new WidthHeiht(1680,1050));
      pcWidthHeight.add(new WidthHeiht(1920,1200));

      pcWidthHeight.add(new WidthHeiht(1600,900));

      pcWidthHeight.add(new WidthHeiht(1024,768));
      pcWidthHeight.add(new WidthHeiht(1680,1050));
      pcWidthHeight.add(new WidthHeiht(1280,800));


      mobileWidthHeight= new ArrayList<>();
      mobileWidthHeight.add(new WidthHeiht(414,736));

  }

    public static int PC=1;
    public static int Mobile=0;


    public static WidthHeiht getEnglishResolution(int pcOrmobile){
            if(pcOrmobile==1){
                return  pcWidthHeight.get(L.randomNumber(0,(pcWidthHeight.size()-1)));
            }else{
                return  mobileWidthHeight.get(L.randomNumber(0,(mobileWidthHeight.size()-1)));
            }
    }

    public  static  class WidthHeiht{
        int width;
        int height;

        public WidthHeiht(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }


}
