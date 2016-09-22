package com.zeestrataca.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class AnimatedChopper extends Image {
    Animation animation = null;
    private float stateTime = 0;

    public AnimatedChopper (Animation animation) {
        super(animation.getKeyFrame(0));
        this.animation = animation;
    }

    @Override
    public void act(float delta) {
        ((TextureRegionDrawable)getDrawable()).setRegion(animation.getKeyFrame(stateTime += delta, true));
        super.act(delta);
    }

    public void flip() {
        TextureRegion[] regions = this.animation.getKeyFrames();
        for (TextureRegion region : regions) {
            region.flip(true, false);
        }
    }
}
