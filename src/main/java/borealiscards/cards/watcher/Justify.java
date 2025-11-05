package borealiscards.cards.watcher;

import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import com.megacrit.cardcrawl.stances.DivinityStance;

public class Justify extends BaseCard {
    public static final String ID = makeID(Justify.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.PURPLE,
            CardType.SKILL,
            CustomRarity.SHOP,
            CardTarget.SELF,
            1
    );

    public Justify() {
        super(ID, info);
        setMagic(4, 2);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player.stance.ID.equals(DivinityStance.STANCE_ID)) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    for (AbstractCard c : AbstractDungeon.player.hand.group) {
                        c.setCostForTurn(0);
                    }
                    this.isDone = true;
                }
            });
        } else addToBot(new ApplyPowerAction(p, p, new MantraPower(p, magicNumber)));
    }
}
