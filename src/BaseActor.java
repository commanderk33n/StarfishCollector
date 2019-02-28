import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;

import java.util.ArrayList;
import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Extend functionality of the LibGDX Actor class.
 **/

public class BaseActor extends Actor
{
    private Animation<TextureRegion> animation;
    private float elapsedTime;
    private boolean animationPaused;

    public BaseActor(float x, float y, Stage s)
    {
        super();
        setPosition(x,y);
        s.addActor(this);

        animation = null;
        elapsedTime = 0;
        animationPaused = false;
    }


    // ----------------------------------------------
    // Animation methods
    // ----------------------------------------------

    public void setAnimation(Animation<TextureRegion> anim)
    {
        animation = anim;
        TextureRegion tr = animation.getKeyFrame(0);
        float w = tr.getRegionWidth();
        float h = tr.getRegionHeight();
        setSize( w,h );
        setOrigin( w/2, h/2 );
    }

    public Animation<TextureRegion> loadAnimationFromFiles(String[] fileNames, float frameDuration, boolean loop)
    {
        int fileCount = fileNames.length;
        Array<TextureRegion> textureArray = new Array<TextureRegion>();
        for (int n = 0; n < fileCount; n++)
        {
         String fileName = fileNames[n];
         Texture texture = new Texture(Gdx.files.internal(fileName));
         texture.setFilter( TextureFilter.Linear, TextureFilter.Linear );
         textureArray.add( new TextureRegion( texture ));
        }

        Animation<TextureRegion> anim = loadAnimation(textureArray, frameDuration, loop);

        return anim;
    }

    public Animation<TextureRegion> loadAnimationFromSheet(String fileName, int rows, int cols, float frameDuration,
                                                           boolean loop)
    {
        Texture texture = new Texture(Gdx.files.internal(fileName), true);
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        int frameWidth = texture.getWidth() / cols;
        int frameHeight = texture.getHeight() /rows;

        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);

        Array<TextureRegion> textureArray = new Array<TextureRegion>();

        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                textureArray.add( temp[r][c] );

        Animation<TextureRegion> anim = loadAnimation(textureArray, frameDuration, loop);

        return anim;
    }

    public Animation<TextureRegion> loadAnimation(Array<TextureRegion> textureArray, float frameDuration,
                                                    boolean loop){
        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);
        if (loop)
            anim.setPlayMode(PlayMode.LOOP);
        else
            anim.setPlayMode(PlayMode.NORMAL);

        if (animation == null)
            setAnimation(anim);

        return anim;
    }

    public Animation<TextureRegion> loadTexture(String fileName)
    {
        String[] fileNames = new String[1];
        fileNames[0] = fileName;
        return loadAnimationFromFiles(fileNames, 1, true);
    }

    public void setAnimationPaused(boolean pause)
    {
        animationPaused = pause;
    }

    public boolean isAnimationFinished()
    {
        return animation.isAnimationFinished(elapsedTime);
    }


    // ----------------------------------------------
    // Actor methods: act and draw
    // ----------------------------------------------

    public void act(float dt)
    {
        super.act( dt );

        if (!animationPaused)
            elapsedTime += dt;
    }

    public void draw(Batch batch, float parentAlpha)
    {
        super.draw( batch, parentAlpha );

        // apply color tint effect
        Color c = getColor();
        batch.setColor(c.r, c.g, c.b, c.a);

        if ( animation != null && isVisible() )
            batch.draw( animation.getKeyFrame(elapsedTime), getX(), getY(), getOriginX(), getOriginY(), getWidth(),
                    getHeight(), getScaleX(), getScaleY(), getRotation() );
    }
}