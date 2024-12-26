package com.avalancherush.game.Views;

import static com.avalancherush.game.Globals.Fonts.BIG_BLACK_FONT;
import static com.avalancherush.game.Globals.GlobalVariables.heightScale;
import static com.avalancherush.game.Globals.GlobalVariables.widthScale;
import static com.avalancherush.game.Globals.Textures.BACKGROUND;
import static com.avalancherush.game.Globals.Textures.HOME_BUTTON;
import static com.avalancherush.game.Globals.Textures.WOOD_BUTTON;

import com.avalancherush.game.Controllers.SinglePlayerController;
import com.avalancherush.game.Enums.EventType;
import com.avalancherush.game.Interfaces.BasicView;
import com.avalancherush.game.MyAvalancheRushGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class SinglePlayerView extends BasicView {
    private SinglePlayerController singlePlayerController;
    private Rectangle playButton;
    private Rectangle homeButton;
    private BitmapFont fontTitle;
    private BitmapFont fontText;
    public SinglePlayerView() {
        this.singlePlayerController = new SinglePlayerController();

        this.playButton = new Rectangle((MyAvalancheRushGame.INSTANCE.getScreenWidth() - WOOD_BUTTON.getWidth() * widthScale) / 2, (MyAvalancheRushGame.INSTANCE.getScreenHeight() - WOOD_BUTTON.getHeight() * heightScale) / 2, WOOD_BUTTON.getWidth() * widthScale, WOOD_BUTTON.getHeight() * heightScale);
        this.homeButton = new Rectangle(50, 50, HOME_BUTTON.getWidth() * widthScale, HOME_BUTTON.getHeight() * heightScale);

        this.fontTitle = BIG_BLACK_FONT;
        this.fontTitle.getData().setScale(1 * heightScale);

        this.fontText = BIG_BLACK_FONT;
        this.fontText.getData().setScale(1.2f * heightScale);
    }

    @Override
    public void render(float delta) {

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        batch.draw(BACKGROUND, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());

        GlyphLayout gameLogoLayout = new GlyphLayout(fontTitle, "Single Player");
        float gameLogoX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - gameLogoLayout.width) / 2;
        float gameLogoY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - gameLogoLayout.height - 20;
        fontTitle.draw(batch, gameLogoLayout, gameLogoX, gameLogoY);

        batch.draw(WOOD_BUTTON, playButton.x, playButton.y, playButton.getWidth(), playButton.getHeight());

        GlyphLayout singlePlayerLayout = new GlyphLayout(fontText, "play");
        float singlePlayerTextX = playButton.x + (playButton.getWidth() - singlePlayerLayout.width) / 2;
        float singlePlayerTextY = playButton.y + (playButton.getHeight() + singlePlayerLayout.height) / 2;
        fontText.draw(batch, singlePlayerLayout, singlePlayerTextX, singlePlayerTextY);

        batch.draw(HOME_BUTTON, homeButton.x, homeButton.y, homeButton.getWidth(), homeButton.getHeight());

        batch.end();
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 touchPos = new Vector3(screenX, screenY, 0);
                orthographicCamera.unproject(touchPos);

                if (playButton.contains(touchPos.x, touchPos.y)) {
                    singlePlayerController.notify(EventType.GAME_SINGLE_PLAYER_CLICK);
                    return true;

                } else if (homeButton.contains(touchPos.x, touchPos.y)) {
                   singlePlayerController.notify(EventType.HOME_BUTTON_CLICK);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void dispose() {
        batch.dispose();
        fontText.dispose();
        fontTitle.dispose();
        HOME_BUTTON.dispose();
        WOOD_BUTTON.dispose();
    }
}







