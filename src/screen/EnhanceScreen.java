package screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import engine.SoundEffect;
import engine.Cooldown;
import engine.Core;
import engine.GameState;

public class EnhanceScreen extends Screen {
    /** Milliseconds between changes in user selection. */
    private static final int SELECTION_TIME = 200;
    /** Height of the interface separation line. */
    private static final int SEPARATION_LINE_HEIGHT = 40;
    /** Current score. */
    private int score;
    /** Player lives left. */
	private double lives;
    /** Time between changes in user selection. */
    private Cooldown selectionCooldown;
    /** Current Number of Enhancement Area&Damage Stone */
    private int valEnhanceArea = 0;
    private int valEnhanceDamage = 0;
    /** Current Level of Enhancement Area&Damage Stone */
    private int lvEnhanceArea = 0;
    private int lvEnhanceDamage = 0;
    /** Settings of Centered Circle Frame */
    private int centeredCircleWidth = 170;
    private int centeredCircleHeight = 170;
    private int centeredCircleX = (this.width - 170) / 2;
    private int centeredCircleY = SEPARATION_LINE_HEIGHT * 2;
    /** Settings of Both Side Circle Frame */
    private int sideCircleWidth = 70;
    private int sideCircleHeight = 70;
    private int leftCircleX = (this.width - 220) / 2;
    private int rightCircleX = this.width - (this.width - 220) / 2 - 70;
    private int sideCircleY = SEPARATION_LINE_HEIGHT * 5;

    private SoundEffect soundEffect;
    /**
     * Constructor, establishes the properties of the screen.
     *
     * @param width
     *               Screen width.
     * @param height
     *               Screen height.
     * @param fps
     *               Frames per second, frame rate at which the game is run.
     */
    public EnhanceScreen(final GameState gameState, final int width, final int height, final int fps) {
        super(width, height, fps);

        // Defaults to play.
        this.returnCode = 8;
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();
        this.score = gameState.getScore();
        this.lives = gameState.getLivesRemaining();

        soundEffect = new SoundEffect();
    }

    /**
     * Starts the action.
     *
     * @return Next screen code.
     */
    public final int run() {
        super.run();

        return this.returnCode;
    }

    /**
     * Updates the elements on screen and checks for events.
     */
    protected final void update() {
        super.update();

        draw();
        if (this.selectionCooldown.checkFinished()
                && this.inputDelay.checkFinished()) {
            if (inputManager.isKeyDown(KeyEvent.VK_UP)
                    || inputManager.isKeyDown(KeyEvent.VK_W)) {
                soundEffect.playButtonClickSound();
                previousVerticalMenuItem();
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_DOWN)
                    || inputManager.isKeyDown(KeyEvent.VK_S)) {
                soundEffect.playButtonClickSound();
                nextVerticalMenuItem();
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_LEFT)
                    || inputManager.isKeyDown(KeyEvent.VK_A)) {
                soundEffect.playButtonClickSound();
                previousHorizontalMenuItem();
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_RIGHT)
                    || inputManager.isKeyDown(KeyEvent.VK_D)) {
                soundEffect.playButtonClickSound();
                nextHorizontalMenuItem();
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
                this.isRunning = false;
                soundEffect.playSpaceButtonSound();
            }
        }
    }

    /**
     * Shifts the focus to the next menu item. (Horizontal Ver.)
     */
    private void nextHorizontalMenuItem() { 
        if (this.returnCode == 8)
            this.returnCode = 9;
        else
            this.returnCode = 8;
    }

    /**
     * Shifts the focus to the next menu item. (Vertical Ver.)
     */
    private void nextVerticalMenuItem() {
        if (this.returnCode == 8 || this.returnCode == 9)
            this.returnCode = 5;
        else if (this.returnCode == 5)
            this.returnCode = 6;
        else if (this.returnCode == 6)
            this.returnCode = 2;
        else
            this.returnCode = 5;
    }

    /**
     * Shifts the focus to the previous menu item. (Horizontal Ver.)
     */
    private void previousHorizontalMenuItem() {
        if (this.returnCode == 8)
            this.returnCode = 9;
        else
            this.returnCode = 8;
    }

    /**
     * Shifts the focus to the previous menu item. (Vertical Ver.)
     */
    private void previousVerticalMenuItem() {
        if (this.returnCode == 8)
            this.returnCode = 8;
        else if (this.returnCode == 9)
            this.returnCode = 9;
        else if (this.returnCode == 2)
            this.returnCode = 6;
        else if (this.returnCode == 6)
            this.returnCode = 5;
        else
            this.returnCode = 8;
    }

    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);
        drawManager.drawLivesbar(this, this.lives);
        drawManager.drawScore(this, this.score);
        drawManager.drawHorizontalLine(this, SEPARATION_LINE_HEIGHT - 1);

        drawManager.drawCircleLine(this, centeredCircleX, centeredCircleY, centeredCircleWidth, centeredCircleHeight, 0);
        drawManager.drawCircleFill(this, leftCircleX, sideCircleY, sideCircleWidth, sideCircleHeight);
        drawManager.drawCircleFill(this, rightCircleX, sideCircleY, sideCircleWidth, sideCircleHeight);
        drawManager.drawCircleLine(this, leftCircleX, sideCircleY, sideCircleWidth, sideCircleHeight, 0);
        drawManager.drawCircleLine(this, rightCircleX, sideCircleY, sideCircleWidth, sideCircleHeight, 0);

        String AreaString = "Area";
		String DamageString = "Damage";
		String EnhanceString = "Enhance";
		String StoneString = "Stone";
        int fontSizeOption = 0;

        drawManager.drawEnhanceStoneString(this, AreaString, 
                                            leftCircleX + sideCircleWidth / 2, sideCircleY + sideCircleHeight + 20, 
                                            Color.GRAY, fontSizeOption);
        drawManager.drawEnhanceStoneString(this, EnhanceString + " " + StoneString, 
                                            leftCircleX + sideCircleWidth / 2, sideCircleY + sideCircleHeight + 40, 
                                            Color.GRAY, fontSizeOption);
        drawManager.drawEnhanceStoneString(this, DamageString, 
                                            rightCircleX + sideCircleWidth / 2, sideCircleY + sideCircleHeight + 20, 
                                            Color.GRAY, fontSizeOption);
        drawManager.drawEnhanceStoneString(this, EnhanceString + " " + StoneString, 
                                            rightCircleX + sideCircleWidth / 2, sideCircleY + sideCircleHeight + 40, 
                                            Color.GRAY, fontSizeOption);

        drawManager.drawEnhanceMenu(this, this.returnCode, 
                                    this.valEnhanceArea, this.valEnhanceDamage, 
                                    this.lvEnhanceArea, this.lvEnhanceDamage);

        drawManager.completeDrawing(this);
    }
}
