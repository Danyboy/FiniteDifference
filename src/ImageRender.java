import java.awt.*;
import java.awt.image.MemoryImageSource;

/**
 * Created by danil on 16.04.14.
 */
public class ImageRender extends Canvas{
    private int[] pix;
    private int X, Y;
    private Image img;

    public ImageRender(double[][] array){
        double max = max(array);
        int a = 0;
        pix = new int [array.length * array[0].length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                        double ds = 255 * Math.abs(array[j][i]/ max);
                        int green = ((int)ds) & 0xff;
                pix[a++] = (255 << 24)|(green << 8);
            }
        }
        img = createImage(new MemoryImageSource(Y, X, pix, 0, X));
//        img = img.getScaledInstance(newX, newY, 1);
    }

    private static double max(double[][] array){
        double max = Double.MIN_VALUE;
        for (double[] doubles : array) {
            for (double current : doubles) {
                if (Math.abs(current) > max) {
                    max = Math.abs(current);
                }
            }
        }
        return max;
    }


    @Override
    public void paint(Graphics g){
        if(img != null){
            g.drawImage(img, 0, 0, this);
        }
    }
}
