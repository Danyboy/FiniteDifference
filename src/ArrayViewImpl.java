import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

/**
 * Created by danil on 16.04.14.
 */
public class ArrayViewImpl extends JFrame {

    int size = 640;

    public ArrayViewImpl(double[][] array){
        Canvas canvas = new ArrayToImageRender(array);
        int X = array.length;
        int Y = array[0].length;

//        canvas.createImage(new MemoryImageSource(Y, X, new ArrayToImageRender(array).getPix(), 0, X));

        DropRender dropRender = null;
        try {
            dropRender = new DropRender();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        setSize(size, size);
        setLocation(140, 60);
        add(canvas, BorderLayout.CENTER);
        getGlassPane().paint(dropRender.drop.createGraphics());

//        setGlassPane(dropRender);
//        getGlassPane().repaint();

        //    add(myBottonJPanel, BorderLayout.PAGE_END);
//        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
        setVisible(true);
    }
}
