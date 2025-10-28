package borealiscards.relics;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

import static borealiscards.BorealisCards.makeID;

public class BonfireHearth extends BaseRelic {
    public static final String ID = makeID(BonfireHearth.class.getSimpleName());

    public BonfireHearth() {
        super(ID, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        if(room instanceof RestRoom) {
            flash();
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.VIOLET.cpy(), true));
            AbstractDungeon.player.increaseMaxHp((int) (AbstractDungeon.player.maxHealth * 0.15), false);
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}