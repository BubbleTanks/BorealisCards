package borealiscards.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static borealiscards.BorealisCards.makeID;

public class TryCatchPower extends BasePower implements NonStackablePower {
    public static final String POWER_ID = makeID(TryCatchPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public TryCatchPower(AbstractCreature owner, AbstractCard c) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
        TryCatch = c;
    }

    public final AbstractCard TryCatch;
    public boolean TriedCatch = false;

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}