import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by danil on 15.05.14.
 */
public class DropRender extends Component{
    BufferedImage dropImage;
    int newSize;
    Drop drop;
    int x, y;

    public DropRender(int size, int x, int y){
        drop = new Drop(size, x, y);
        x = drop.getX();
        y = drop.getY();
        newSize = drop.getDropSize(); //TODO added coefficient
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
        g.drawOval(x, y, newSize, newSize );
//        g.drawImage(dropImage, x, y, null);
    }


}
