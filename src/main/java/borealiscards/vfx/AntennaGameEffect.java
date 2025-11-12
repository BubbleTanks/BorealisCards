package borealiscards.vfx;

import basemod.ReflectionHacks;
import borealiscards.relics.AntennaHeadband;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;
import com.megacrit.cardcrawl.vfx.campfire.CampfireRecallEffect;

import java.util.ArrayList;

public class AntennaGameEffect extends AbstractGameEffect {

    public AntennaGameEffect() {
    }

    public void update() {
        for(AbstractGameEffect abeff : AbstractDungeon.topLevelEffects) {
            if (abeff instanceof ObtainKeyEffect) {
                Settings.hasRubyKey = true;
                break;
            }
        }

        for(AbstractGameEffect abeff : AbstractDungeon.effectList) {
            if (abeff instanceof CampfireRecallEffect) {
                Settings.hasRubyKey = true;
                break;
            }
        }

        RestRoom room = ((RestRoom) AbstractDungeon.getCurrRoom());

        ArrayList<AbstractCampfireOption> buttons = ReflectionHacks.getPrivate(room.campfireUI, CampfireUI.class, "buttons");
        buttons.clear();
        ReflectionHacks.privateMethod(CampfireUI.class, "initializeButtons").invoke(room.campfireUI);
        room.campfireUI.reopen();
        room.cutFireSound();

        if (AbstractDungeon.player.hasRelic(AntennaHeadband.ID)) {
            if (AbstractDungeon.player.maxHealth <= 2) {
                while (AbstractDungeon.player.currentHealth > 0) {
                    AbstractDungeon.player.damage(new DamageInfo(null, 99999, DamageInfo.DamageType.HP_LOSS));
                    // WHERE YOU THINK YOU'RE GOING FLINT LOCKWOOD
                }
                AbstractDungeon.player.maxHealth = 0;
                AbstractDungeon.player.currentHealth = 0;
            } else AbstractDungeon.player.decreaseMaxHealth(2);
        }

        this.isDone = true;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
