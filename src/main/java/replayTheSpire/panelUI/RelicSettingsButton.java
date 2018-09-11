package replayTheSpire.panelUI;

import basemod.*;
import com.megacrit.cardcrawl.helpers.*;

import java.util.List;
import java.util.function.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.core.*;

public class RelicSettingsButton implements IUIElement
{
	public static RelicSettingsButton activeButton;
	public Texture image;
    public Texture outline;
    public float x;
    public float y;
    public float w;
    public float h;
    private Hitbox hitbox;
    public AbstractRelic relic;
    public List<IUIElement> elements;
    public boolean isSelected;

    public RelicSettingsButton(final AbstractRelic relic, final List<IUIElement> elements) {
    	this(relic, 450.0f, 625.0f, 100.0f, 100.0f, elements);
    }
    
    public RelicSettingsButton(final AbstractRelic relic, final float x, final float y, final float width, final float height, final List<IUIElement> elements) {
        this(relic.img, relic.outlineImg, x, y, width, height, elements);
        this.relic = relic;
        this.w *= 1.5f;
        this.h *= 1.5f;
    }
    public RelicSettingsButton(final Texture image, final Texture outline, final float x, final float y, final float width, final float height, final List<IUIElement> elements) {
    	this.relic = null;
        this.image = image;
        this.outline = outline;
        this.x = x;
        this.y = y;
        this.w = width;
        this.h = height;
        this.elements = elements;
        this.isSelected = false;
        this.hitbox = new Hitbox((float)this.x, (float)this.y, (float)this.w, (float)this.h);
    }
    
    public void render(final SpriteBatch sb) {
    	if (this.outline != null) {
            sb.setColor(new Color(0.0f, 0.0f, 0.0f, 1.0f));
            sb.draw(this.outline, this.x * Settings.scale, this.y * Settings.scale, this.w * Settings.scale, this.h * Settings.scale);
        }
        sb.setColor(Color.WHITE);
        sb.draw(this.image, this.x * Settings.scale, this.y * Settings.scale, this.w * Settings.scale, this.h * Settings.scale);
        this.hitbox.render(sb);
        if (this.isSelected) {
        	for (IUIElement element : this.elements) {
        		element.render(sb);
        	}
        }
    }
    
    public void update() {
        this.hitbox.update();
        if (this.hitbox.hovered && InputHelper.justClickedLeft) {
            CardCrawlGame.sound.play("UI_CLICK_1");
            if (this.isSelected) {
            	this.isSelected = false;
            } else {
            	if (RelicSettingsButton.activeButton != null) {
                	RelicSettingsButton.activeButton.isSelected = false;
                }
                RelicSettingsButton.activeButton = this;
                this.isSelected = true;
            }
        }
        if (this.isSelected) {
        	for (IUIElement element : this.elements) {
        		element.update();
        	}
        }
    }
    
    public int renderLayer() {
        return 1;
    }
    
    public int updateOrder() {
        return 1;
    }
}