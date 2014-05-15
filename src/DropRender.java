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
    BufferedImage drop;
    int x = 0, y = 0;

    public DropRender() throws FileNotFoundException {
        drop = null;
        try {
            drop = ImageIO.read(new File("Victor.jpg"));
        } catch (IOException e) {
            System.out.println("No file");
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(drop, x, y, null);
    }


}
