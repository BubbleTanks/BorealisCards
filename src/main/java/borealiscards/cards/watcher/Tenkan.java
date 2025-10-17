package borealiscards.cards.watcher;

import borealiscards.cards.BaseCard;
import borealiscards.patches.PreviousStancePatch;
import borealiscards.stances.TensionStance;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.CalmStance;
import com.megacrit.cardcrawl.stances.DivinityStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.stances.WrathStance;

public class Tenkan extends BaseCard {
    public static final String ID = makeID(Tenkan.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.PURPLE,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            0
    );

    public Tenkan() {
        super(ID, info);
        setSelfRetain(true);
        setExhaust(true, false);
    }

    public void applyPowers() {

        if(!upgraded) {
            if (PreviousStancePatch.previousStance.ID == NeutralStance.STANCE_ID) {
                rawDescription = cardStrings.DESCRIPTION + " NL " + String.format(cardStrings.EXTENDED_DESCRIPTION[0]);
            } else if (PreviousStancePatch.previousStance.ID == CalmStance.STANCE_ID) {
                rawDescription = cardStrings.DESCRIPTION + " NL " + String.format(cardStrings.EXTENDED_DESCRIPTION[1]);
            } else if (PreviousStancePatch.previousStance.ID == WrathStance.STANCE_ID) {
                rawDescription = cardStrings.DESCRIPTION + " NL " + String.format(cardStrings.EXTENDED_DESCRIPTION[2]);
            } else if (PreviousStancePatch.previousStance.ID == TensionStance.STANCE_ID) {
                rawDescription = cardStrings.DESCRIPTION + " NL " + String.format(cardStrings.EXTENDED_DESCRIPTION[3]);
            } else if (PreviousStancePatch.previousStance.ID == DivinityStance.STANCE_ID) {
                rawDescription = cardStrings.DESCRIPTION + " NL " + String.format(cardStrings.EXTENDED_DESCRIPTION[4]);
            } else rawDescription = cardStrings.DESCRIPTION + " NL " + String.format(cardStrings.EXTENDED_DESCRIPTION[5]);
        } else {
            if (PreviousStancePatch.previousStance.ID == NeutralStance.STANCE_ID) {
                rawDescription = cardStrings.UPGRADE_DESCRIPTION + " NL " + String.format(cardStrings.EXTENDED_DESCRIPTION[0]);
            } else if (PreviousStancePatch.previousStance.ID == CalmStance.STANCE_ID) {
                rawDescription = cardStrings.UPGRADE_DESCRIPTION + " NL " + String.format(cardStrings.EXTENDED_DESCRIPTION[1]);
            } else if (PreviousStancePatch.previousStance.ID == WrathStance.STANCE_ID) {
                rawDescription = cardStrings.UPGRADE_DESCRIPTION + " NL " + String.format(cardStrings.EXTENDED_DESCRIPTION[2]);
            } else if (PreviousStancePatch.previousStance.ID == TensionStance.STANCE_ID) {
                rawDescription = cardStrings.UPGRADE_DESCRIPTION + " NL " + String.format(cardStrings.EXTENDED_DESCRIPTION[3]);
            } else if (PreviousStancePatch.previousStance.ID == DivinityStance.STANCE_ID) {
                rawDescription = cardStrings.UPGRADE_DESCRIPTION + " NL " + String.format(cardStrings.EXTENDED_DESCRIPTION[4]);
            } else rawDescription = cardStrings.UPGRADE_DESCRIPTION + " NL " + String.format(cardStrings.EXTENDED_DESCRIPTION[5]);
        }

        initializeDescription();

        super.applyPowers();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChangeStanceAction(PreviousStancePatch.previousStance));
    }
}
