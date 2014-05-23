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

    public ArrayViewImpl(double[][] heat){
        arrayPainter = new ArrayToImageRender(heat);
        int X = heat.length;
        int Y = heat[0].length;
        resizeCoefficient = size / X;

        int dropSize = 5;

        dropRender = new DropRender(dropSize, heat.length / 2 - dropSize / 2, heat[1].length / 2 - dropSize / 2);
        dropRender.drop.setHeat(heat);
        arrayPainter.setDrop(dropRender);

        setSize(size, size);
        setLocation(140, 60);
        add(arrayPainter, BorderLayout.CENTER);

//TODO cant draw component with transparent background
//        setGlassPane(dropRender);
//        getGlassPane().setBackground(new Color(0,0,0,0));
//        getGlassPane().repaint();
//        getGlassPane().setVisible(true);
//        getGlassPane().setP
//        add(getGlassPane());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
        setVisible(true);
    }

    public void nextStep(){
        dropRender.drop.nextStep();
        arrayPainter.repaint();
//        add(arrayPainter, BorderLayout.CENTER);
//        setVisible(false);
//        setVisible(true);
    }

}
