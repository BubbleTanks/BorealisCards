package borealiscards.cards.ironclad;

import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.powers.BloodHuntPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BloodHunting extends BaseCard {
    public static final String ID = makeID(BloodHunting.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.RED,
            CardType.SKILL,
            CustomRarity.SHOP,
            CardTarget.ENEMY,
            2
    );

    public BloodHunting() {
        super(ID, info);
        setMagic(2,1);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new BloodHuntPower(m, magicNumber)));
    }
}


