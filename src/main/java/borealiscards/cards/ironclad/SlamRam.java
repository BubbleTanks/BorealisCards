package borealiscards.cards.ironclad;

import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class SlamRam extends BaseCard {
    public static final String ID = makeID(SlamRam.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.RED,
            CardType.SKILL,
            CustomRarity.SHOP,
            CardTarget.SELF,
            1
    );

    public SlamRam() {
        super(ID, info);
        setMagic(3,-1);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {

                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    if (c.type == CardType.ATTACK) {
                        addToBot(new NewQueueCardAction(c, true, false, true));
                    }
                }

                addToBot(new ApplyPowerAction(p, p, new WeakPower(p, magicNumber, false)));
                addToBot(new ApplyPowerAction(p, p, new VulnerablePower(p, magicNumber, false)));
                addToBot(new ApplyPowerAction(p, p, new FrailPower(p, magicNumber, false)));

                this.isDone = true;
            }
        });
    }
}


