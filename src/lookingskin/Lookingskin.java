package lookingskin;

/**
 *
 * @author admin
 */
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import javax.imageio.ImageIO;
import processing.core.*;
//import processing.opengl.PGraphics2D;

public class Lookingskin extends PApplet {

    private static int seed = 0;

    //https://kdp.amazon.com/en_US/help/topic/G200645690
    //2560*1600 (though it says 2500...)
    //"Ideal dimensions for cover files are 2,560 pixels in height x 1,600 pixels in width."
    //"For best quality, particularly on high definition devices, your image should be 2500 pixels in height."
    //Ratio: 1.6 (which for 1600 width is 2560)
    //50mb file size limit
    //72dpi (though the 2560*1600 dimensions aren't divisible by that)
    //If this is the ideal, we can scale down by that for viewing
    private static int outputheight = 2560;
    private static int outputwidth = 1600;

    //Where 1 is the scale above
//    private static float scalefactor = 1f;
    private static float scalefactor = 0.65f;

    //Text template for the generative lines to avoid
    private PImage textimage;

    private File file;
    private int[][] template_pixels;

    //Load template image into 2D int array (faster access than Processing's image get method, which is sloooow)
    //https://stackoverflow.com/questions/15002706/convert-an-image-to-a-2d-array-and-then-get-the-image-back-in-java/15005373
    public int[][] compute(File file) {

        try {
            BufferedImage img = ImageIO.read(file);
            Raster raster = img.getData();
            int w = raster.getWidth(), h = raster.getHeight();

            System.out.println("width: " + w + ", height: " + h);

            int[][] pixels = new int[w][h];
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    pixels[x][y] = raster.getSample(x, y, 0);
                }
            }

            return pixels;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void settings() {
//        size(1000, 1000);
        System.out.println((int) (outputwidth * scalefactor) + "," + (int) (outputheight * scalefactor));

//        size((int) (outputwidth * scalefactor), (int) (outputheight * scalefactor));
        size((int) (outputwidth * scalefactor), (int) (outputheight * scalefactor), "processing.opengl.PGraphics2D");
        smooth(8);

    }

    public void setup() {
        
        frameRate(1000);

        //set initial output location, top left
        surface.setLocation(0, 0);

        noiseSeed(seed);
        randomSeed(seed);

        //only need to set background once
        background(255);

        stroke(0);
        strokeWeight(10);
        fill(100);

        textimage = loadImage("images/cover_texttemplate.png");

        file = new File("images/cover_texttemplate.png");

        template_pixels = compute(file);

    }

    public void draw() {

        scale(scalefactor);

//        circle(1600/2, 2560/2, 1000);
        //https://www.javatpoint.com/java-object-getclass-method
        //It's just an java.lang.Integer object, not the color thing that's used in sketches
//        Object obj = textimage.get((int) random(1600), (int) random(2560));
//        Class a = obj.getClass(); 
//        System.out.println("Class of Object obj is : " + a.getName());   
//        int x = (int) random(1600), y = (int) random(2560);
//
//        int i = textimage.get(x, y);
//
////        System.out.println("Colour: " + i);
////        System.out.println("Colour: " + i.intValue());
//        if (i == -1) {
////            System.out.println("ping!");
//            point(x, y);
//        }
//

        //Check direct array access instead. Ah, nice single 0-255 number, perfect for what we need here
//        System.out.println("template pixel: " + template_pixels[x][y]);
//        if (template_pixels[x][y] != 0) {
////            System.out.println("ping!");
//            point(x, y);
//        }
        
        

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.out.println("View height: " + (outputheight * scalefactor));

        PApplet.main(new String[]{"lookingskin.Lookingskin"});
    }

}
