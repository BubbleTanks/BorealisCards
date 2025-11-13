package borealiscards.cards.defect;

import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.powers.TryCatchPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import java.util.ArrayList;

public class TryCatch extends BaseCard {
    public static final String ID = makeID(TryCatch.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.SKILL,
            CustomRarity.SHOP,
            CardTarget.SELF,
            0
    );

    public TryCatch() {
        super(ID, info);
        updateTryDescription();
        setInnate(false,true);
    }

    public ArrayList<AbstractOrb> CaughtOrbs = new ArrayList<>();

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractOrb o : CaughtOrbs) {
            addToBot(new ChannelAction(o.makeCopy()));
        }
        addToBot(new ApplyPowerAction(p, p, new TryCatchPower(p, this)));
    }

    public void applyPowers() {
        updateTryDescription();
        super.applyPowers();
    }

    public void upgrade() {
        updateTryDescription();
        super.upgrade();
    }

    public void updateTryDescription() {
        String tryCatchString = cardStrings.EXTENDED_DESCRIPTION[0];
        for (AbstractOrb o : CaughtOrbs) {
            String orbName = "*"+o.name;
            orbName.replace(" ", " *");

            if(CaughtOrbs.size() == 1) {
                tryCatchString += orbName + LocalizedStrings.PERIOD + cardStrings.EXTENDED_DESCRIPTION[5];
            } else if (!CaughtOrbs.isEmpty()) {
                if (CaughtOrbs.indexOf(o) != CaughtOrbs.size() - 1) {
                    tryCatchString += orbName;
                    if (CaughtOrbs.size() != 2) tryCatchString += cardStrings.EXTENDED_DESCRIPTION[1];
                } else {
                    if (CaughtOrbs.size() == 2) tryCatchString += cardStrings.EXTENDED_DESCRIPTION[6];
                    tryCatchString += cardStrings.EXTENDED_DESCRIPTION[2] + orbName + LocalizedStrings.PERIOD + cardStrings.EXTENDED_DESCRIPTION[5];
                }
            }
        }
        if (CaughtOrbs.isEmpty()) {
            tryCatchString = cardStrings.EXTENDED_DESCRIPTION[3] + cardStrings.EXTENDED_DESCRIPTION[5];
        }
        if(upgraded) tryCatchString = cardStrings.EXTENDED_DESCRIPTION[4] + tryCatchString;
        rawDescription = tryCatchString;
        initializeDescription();
    }

    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard retVal = super.makeStatEquivalentCopy();
        ((TryCatch)retVal).CaughtOrbs = new ArrayList<>(this.CaughtOrbs);
        ((TryCatch)retVal).updateTryDescription();
        return retVal;
    }
}
