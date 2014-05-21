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

        DropRender dropRender = new DropRender();



        setSize(size, size);
        setLocation(140, 60);
        add(canvas, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
        setVisible(true);
    }
}
