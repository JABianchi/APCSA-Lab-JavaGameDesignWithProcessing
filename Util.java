
import processing.core.PImage;

public class Util{

  public static String toStringPImage(PImage img){
    if(img == null){
      return "PImage is null !!!!";
    }
    return "PImage at: " + img + "\t(w:" + img.width + ",h:" + img.height + ")";
  }

} // end of Util class
