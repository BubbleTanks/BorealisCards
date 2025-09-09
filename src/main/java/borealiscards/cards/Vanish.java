package borealiscards.cards;

import borealiscards.patches.DiscardedThisCombatPatch;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class Vanish extends BaseCard {
    public static final String ID = makeID(Vanish.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.GREEN,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    public Vanish() {
        super(ID, info);
        setMagic(0,3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(DiscardedThisCombatPatch.cardsDiscardedThisCombat+magicNumber > 0) {
            addToBot(new ApplyPowerAction(m, p, new PoisonPower(m, p, DiscardedThisCombatPatch.cardsDiscardedThisCombat + magicNumber), DiscardedThisCombatPatch.cardsDiscardedThisCombat + magicNumber, AbstractGameAction.AttackEffect.POISON));
        }
    }

    public void applyPowers() {
        int poisonCount = DiscardedThisCombatPatch.cardsDiscardedThisCombat+magicNumber;

        if (poisonCount > 0) {
            rawDescription = cardStrings.DESCRIPTION + String.format(cardStrings.EXTENDED_DESCRIPTION[0], poisonCount);
            initializeDescription();
        }

        super.applyPowers();
    }

    public void onMoveToDiscard() {
        rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

}
