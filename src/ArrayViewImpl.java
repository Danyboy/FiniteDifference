import javax.swing.*;
import java.awt.*;

/**
 * Created by danil on 16.04.14.
 */
public class ArrayViewImpl extends JFrame {

    public static int size = 640;
    public static int resizeCoefficient; //TODO add Y coef
    private DropRender dropRender;
    private ArrayToImageRender arrayPainter;

    public ArrayViewImpl(double[][] heat) {
        arrayPainter = new ArrayToImageRender(heat);
        int X = heat.length;
        int Y = heat[0].length;
        resizeCoefficient = size / X;

        int dropSize = 5;

        dropRender = new DropRender(dropSize, heat.length / 2 - dropSize / 2 - 5, heat[1].length / 2 - dropSize / 2);
        dropRender.drop.setHeat(heat);
        arrayPainter.setDrop(dropRender);

        setSize(size, size);
        setLocation(140, 60);
        add(arrayPainter, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
        setVisible(true);
    }

    public void nextStep() {
        dropRender.drop.nextSteps();
        arrayPainter.repaint();
    }

}
