package borealiscards.cards.defect;

import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class OverloadedCircuits extends BaseCard {
    public static final String ID = makeID(OverloadedCircuits.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.ATTACK,
            CustomRarity.SHOP,
            CardTarget.ENEMY,
            1
    );

    public OverloadedCircuits() {
        super(ID, info);
        setDamage(4,2);
    }

    public void applyPowers() {
        updateCircuitDescription();
        super.applyPowers();
    }

    private void updateCircuitDescription() {
        int hits = 1;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == CardType.POWER) {
                hits *= 2;
            }
        }

        String circuitString = cardStrings.EXTENDED_DESCRIPTION[0];
        circuitString += hits + cardStrings.EXTENDED_DESCRIPTION[1];
        if (hits > 1) {
            circuitString += cardStrings.EXTENDED_DESCRIPTION[2];
        }
        circuitString += cardStrings.EXTENDED_DESCRIPTION[3];
        circuitString = cardStrings.DESCRIPTION + circuitString;
        rawDescription = circuitString;
        initializeDescription();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int hits = 1;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == CardType.POWER) {
                hits *= 2;
            }
        }
        for (int i = hits; i > 0; i--) {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        }
    }
}
