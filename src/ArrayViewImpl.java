import javax.swing.*;
import java.awt.*;

/**
 * Created by danil on 16.04.14.
 */
public class ArrayViewImpl extends JFrame {

    int size = 640;

    public ArrayViewImpl(double[][] array){
        Canvas canvas = new ImageRender(array);
        canvas.setPreferredSize(new Dimension(size, size));
        canvas.setSize(size, size);
        setLocation(140, 60);
        add(canvas, BorderLayout.CENTER);
        //    add(myBottonJPanel, BorderLayout.PAGE_END);
//        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
