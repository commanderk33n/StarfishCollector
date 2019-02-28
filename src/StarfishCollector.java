
public class StarfishCollector extends GameBeta
{
    private Turtle turtle;
    private Starfish starfish;
    private BaseActor ocean;

    public void initialize()
    {
        ocean = new BaseActor(0,0,mainStage);
        ocean.loadTexture( "assets/water.jpg");
        ocean.setSize(800,600);

        starfish = new Starfish(380,380, mainStage);
        turtle = new Turtle(20,20, mainStage);
    }

    public void update(float dt)
    {

    }
}
