import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Test;
import com.example.tzancashootingv1.Login;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class test {
    @Test
    public void testPlayer(){
        Rectangle rect = new Rectangle(40, 40, Color.RED);
        assertEquals(rect.getTranslateY(),40,40,"Wrong width");
        assertEquals(rect.getTranslateX(),40,40,"Wrong height");
    }
}
