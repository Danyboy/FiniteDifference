import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by danil on 15.05.14.
 */
public class DropRender extends JComponent {
    BufferedImage dropImage;
    int resizedDrop;
    Drop drop;

    public DropRender(int size, int x, int y) {
        drop = new Drop(size, x, y);
        resizedDrop = drop.getDropSize() * ArrayViewImpl.resizeCoefficient; //TODO add coefficient
    }

    public DropRender() {
        this(2, 0, 0);
    }

    public DropRender(File file) throws FileNotFoundException {
        drop = null;
        try {
            dropImage = ImageIO.read(file); //new File("Victor.jpg")
        } catch (IOException e) {
            System.out.println("No file");
        }
    }

    Color blue = new Color(0, 0, 250, 120);
    Color white = new Color(250, 250, 250, 120);

    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(0, 0, 250, 120));
//        g.fillOval(drop.getX() * ArrayViewImpl.resizeCoefficient, drop.getY() * ArrayViewImpl.resizeCoefficient, resizedDrop, resizedDrop);

        boolean drawPath = true;
        if (drawPath) {
            int[][] path = drop.dropPath;
            int steps = path.length;
            int dropPosition = ArrayViewImpl.resizeCoefficient;
            for (int i = 0; i < path.length; i++) {
                int currentStepDropSize = resizedDrop * (steps - (int) (i / 1.5)) / steps;    // i / 2 is integer
                int[] step = path[i];
                g.setColor(new Color(0, 0, 250, 120 * (steps - (int) (i / 1.5)) / steps));
                g.fillOval(step[0] * dropPosition, step[1] * dropPosition, currentStepDropSize, currentStepDropSize);
            }
        }
    }


}
