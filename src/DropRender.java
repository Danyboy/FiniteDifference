import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by danil on 15.05.14.
 */
public class DropRender extends JComponent {
    BufferedImage dropImage;
    int newSize;
    Drop drop;

    public DropRender(int size, int x, int y){
        drop = new Drop(size, x, y);
        newSize = drop.getDropSize() * ArrayViewImpl.resizeCoefficient; //TODO add coefficient
    }

    public DropRender(){
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

    @Override
    public void paint(Graphics g) {
//        g.drawOval(x, y, newSize, newSize );
//        g.drawImage(dropImage, x, y, null);
//        g.fillOval(320,320,120,120);

        g.setColor(new Color(0, 0, 250, 120));
        g.fillOval(drop.getX() * ArrayViewImpl.resizeCoefficient, drop.getY() * ArrayViewImpl.resizeCoefficient, newSize, newSize);
    }


}
