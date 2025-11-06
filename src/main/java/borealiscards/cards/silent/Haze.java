package borealiscards.cards.silent;

import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;

public class Haze extends BaseCard {
    public static final String ID = makeID(Haze.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.GREEN,
            CardType.SKILL,
            CustomRarity.SHOP,
            CardTarget.SELF,
            1
    );

    public Haze() {
        super(ID, info);
        setMagic(8,-3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseBlockAction(p, p, magicNumber));
        addToBot(new ApplyPowerAction(p, p, new BlurPower(p, 2)));
    }
}
