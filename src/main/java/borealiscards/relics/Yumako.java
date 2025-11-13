package borealiscards.relics;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;

import static borealiscards.BorealisCards.makeID;

public class Yumako extends BaseRelic {
    public static final String ID = makeID(Yumako.class.getSimpleName());

    public Yumako() {
        super(ID, RelicTier.UNCOMMON, LandingSound.FLAT);
        counter = 3;
    }

    public void onEnterRoom(AbstractRoom room) {
        if (counter == 0) {
            flash();
            usedUp();
            counter = -1;
        }
        if (!usedUp) {
            this.flash();
            AbstractDungeon.player.gainGold(18);
            counter--;
        }
    }

    public boolean canSpawn() {
        if (super.canSpawn()) {
            return (Settings.isEndless || AbstractDungeon.floorNum <= 48) && !(AbstractDungeon.getCurrRoom() instanceof ShopRoom);
        }
        return false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}