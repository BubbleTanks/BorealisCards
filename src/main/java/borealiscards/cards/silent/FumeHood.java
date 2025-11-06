package borealiscards.cards.silent;

import borealiscards.cards.BaseCard;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public class FumeHood extends BaseCard {
    public static final String ID = makeID(FumeHood.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.GREEN,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    public FumeHood() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int i = 0;
        for (AbstractPower r : m.powers) {
            if (r.ID == PoisonPower.POWER_ID) {
                i += r.amount;
            }
        }
        if (i > 0) {
            addToBot(new GainBlockAction(p, i));
        } else AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, cardStrings.EXTENDED_DESCRIPTION[0], true));
    }
}
