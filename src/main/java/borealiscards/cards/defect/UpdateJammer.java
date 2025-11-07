package borealiscards.cards.defect;

import basemod.abstracts.CustomSavable;
import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.powers.UpdateJammerPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class UpdateJammer extends BaseCard implements CustomSavable<Boolean> {
    public static final String ID = makeID(UpdateJammer.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.POWER,
            CustomRarity.EXOTIC,
            CardTarget.SELF,
            4
    );

    public static boolean hasWarned = false;

    public UpdateJammer() {
        super(ID, info);
        setCostUpgrade(3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new UpdateJammerPower(p)));
    }


    @Override
    public Boolean onSave() {
        return hasWarned;
    }

    @Override
    public void onLoad(Boolean b) {
        hasWarned = b;
    }
}

// wawa :3
