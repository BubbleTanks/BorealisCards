package borealiscards.cards.defect;

import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.powers.ElectricBaghnakhsPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ElectricBaghnakhs extends BaseCard {
    public static final String ID = makeID(ElectricBaghnakhs.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.ATTACK,
            CustomRarity.SHOP,
            CardTarget.ENEMY,
            1
    );

    public ElectricBaghnakhs() {
        super(ID, info);
        setDamage(5);
        setCostUpgrade(0);
        setMagic(2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        addToBot(new ApplyPowerAction(p, p, new ElectricBaghnakhsPower(p, magicNumber)));
    }
}
