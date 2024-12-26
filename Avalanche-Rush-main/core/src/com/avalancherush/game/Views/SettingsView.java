package com.avalancherush.game.Views;

import com.avalancherush.game.Controllers.SettingsController;
import com.avalancherush.game.Enums.EventType;

import static com.avalancherush.game.Globals.Fonts.BIG_BLACK_FONT;
import static com.avalancherush.game.Globals.GlobalVariables.heightScale;
import static com.avalancherush.game.Globals.GlobalVariables.widthScale;
import static com.avalancherush.game.Globals.Textures.BACKGROUND;
import static com.avalancherush.game.Globals.Textures.HOME_BUTTON;
import static com.avalancherush.game.Globals.Textures.VOLUME_DOWN_BUTTON;
import static com.avalancherush.game.Globals.Textures.VOLUME_UP_BUTTON;

import com.avalancherush.game.Interfaces.BasicView;
import com.avalancherush.game.MyAvalancheRushGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class SettingsView extends BasicView {
    private SettingsController settingsController;
    private Rectangle homeButton;
    private Rectangle volumeUpButton;
    private Rectangle volumeDownButton;
    private BitmapFont fontTitle;

    public SettingsView() {
        this.settingsController = new SettingsController();

        this.homeButton = new Rectangle(50, 50, HOME_BUTTON.getWidth() * widthScale, HOME_BUTTON.getHeight() * heightScale);
        int totalButtonHeight = (int) ((VOLUME_UP_BUTTON.getHeight() + VOLUME_DOWN_BUTTON.getHeight() - 30) * heightScale);
        int startY = (int) ((MyAvalancheRushGame.INSTANCE.getScreenHeight() - totalButtonHeight) / 2 + VOLUME_DOWN_BUTTON.getHeight() * heightScale / 2);

        this.volumeUpButton = new Rectangle((float) (MyAvalancheRushGame.INSTANCE.getScreenWidth() - VOLUME_UP_BUTTON.getWidth() * widthScale) / 2, startY, VOLUME_UP_BUTTON.getWidth() * widthScale, VOLUME_UP_BUTTON.getHeight() * heightScale);


        int buttonSpacing = 40;
        int volumeButtonWidth = (int) (VOLUME_UP_BUTTON.getWidth() * widthScale);
        int totalWidth = volumeButtonWidth * 2 + buttonSpacing;
        int startX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - totalWidth) / 2;
        this.volumeUpButton = new Rectangle(startX, startY, VOLUME_UP_BUTTON.getWidth() * widthScale, VOLUME_UP_BUTTON.getHeight() * heightScale);
        this.volumeDownButton = new Rectangle(startX + volumeButtonWidth + buttonSpacing, startY, VOLUME_DOWN_BUTTON.getWidth() * widthScale, VOLUME_DOWN_BUTTON.getHeight() * heightScale);
        fontTitle = BIG_BLACK_FONT;
        fontTitle.getData().setScale(1 * heightScale);
    }

    @Override
    public void render(float delta) {

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        batch.draw(BACKGROUND,0,0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());
        GlyphLayout gameLogoLayout = new GlyphLayout(fontTitle, "Settings");
        float gameLogoX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - gameLogoLayout.width) / 2;
        float gameLogoY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - gameLogoLayout.height - 20;
        fontTitle.draw(batch, gameLogoLayout, gameLogoX, gameLogoY);

        batch.draw(HOME_BUTTON, homeButton.x, homeButton.y, homeButton.getWidth(), homeButton.getHeight());
        batch.draw(VOLUME_UP_BUTTON, volumeUpButton.x, volumeUpButton.y, volumeUpButton.getWidth(), volumeUpButton.getHeight());
        batch.draw(VOLUME_DOWN_BUTTON, volumeDownButton.x, volumeDownButton.y, volumeDownButton.getWidth(), volumeDownButton.getHeight());

        batch.end();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 touchPos = new Vector3(screenX, screenY, 0);
                orthographicCamera.unproject(touchPos);

                if (homeButton.contains(touchPos.x, touchPos.y)) {
                    settingsController.notify(EventType.HOME_BUTTON_CLICK);
                    return true;
                } else if (volumeUpButton.contains(touchPos.x, touchPos.y)) {
                    settingsController.notify(EventType.VOLUME_UP);
                    return true;
                } else if (volumeDownButton.contains(touchPos.x, touchPos.y)) {
                    settingsController.notify(EventType.VOLUME_DOWN);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void dispose() {
        batch.dispose();
        BACKGROUND.dispose();
        HOME_BUTTON.dispose();
        VOLUME_UP_BUTTON.dispose();
        VOLUME_DOWN_BUTTON.dispose();
        fontTitle.dispose();
    }
}


