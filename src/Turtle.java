import com.badlogic.gdx.scenes.scene2d.Stage;

public class Turtle extends BaseActor {

    public Turtle(float x, float y, Stage s)
    {
        super(x, y, s);

        String[] filenames =
                {"assets/turtle-1.png", "assets/turtle-2.png", "assets/turtle-3.png",
                        "assets/turtle-4.png", "assets/turtle-5.png", "assets/turtle-6.png"};

        loadAnimationFromFiles(filenames, 0.1f, true);
    }
}
