import java.awt.*;
import java.awt.image.MemoryImageSource;

/**
 * Created by danil on 16.04.14.
 */
public class ArrayToImageRender extends Canvas {
    private int[] pix;
    private int X, Y;
    private Image img;
    private DropRender drop;

    public ArrayToImageRender(double[][] array){
        double max = max(array);
        X = array.length;
        Y = array[0].length;
        int a = 0;
        pix = new int [array.length * array[0].length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                double ds = 255 * Math.abs(array[j][i]/ max);
                int red = ((int)ds) & 0xff; //some magic, get the last 8 bits.
                pix[a++] = (255 << 24)|(red << 16);
            }
        }
        int size = 640;
        img = createImage(new MemoryImageSource(Y, X, pix, 0, X));
        img = img.getScaledInstance(size, size, 1);
    }

    private static double max(double[][] array){
        double max = Double.MIN_VALUE;
        for (double[] doubles : array) {
            for (double current : doubles) {
                if (Math.abs(current) > max) {
                    max = Math.abs(current);
//                    System.out.println(max);
                }
            }
        }
        return max;
    }


    @Override
    public void paint(Graphics g){
        if(img != null){
            g.drawImage(img, 0, 0, this);
            drop.paint(g);
//            g.setColor(new Color(0, 0, 250, 120));
//            g.fillOval(320,320,120,120);
        }
    }

    public void setDrop(DropRender drop) {
        this.drop = drop;
    }
}
