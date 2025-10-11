package borealiscards.potions;

import borealiscards.actions.DecreaseMaxHPAction;
import borealiscards.vfx.NightEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static borealiscards.BorealisCards.makeID;

public class VialOfNight extends BasePotion{
    public static final String ID = makeID(VialOfNight.class.getSimpleName());
    public VialOfNight() {
        super(ID, 2, PotionRarity.PLACEHOLDER, PotionSize.T, Color.BLACK.cpy(), null, null);
    }

    @Override
    public String getDescription() {
        return String.format(DESCRIPTIONS[0], potency, potency);
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            addToBot(new VFXAction(AbstractDungeon.player, new NightEffect(AbstractDungeon.player), 1.0F));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, potency), potency));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, potency), potency));
            addToBot(new DecreaseMaxHPAction(AbstractDungeon.player, 2));
            addToBot(new ObtainPotionAction(new VialOfNight()));
        }

    }
}
