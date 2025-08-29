package borealiscards.actions;

import borealiscards.powers.RondelmancyPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class RondelmancyAction extends AbstractGameAction {

    private final AbstractPower power;

    public RondelmancyAction(AbstractPower power) {
        this.power = power;
    }


    @Override
    public void update() {
        ((RondelmancyPower)power).rondelmancyVar = false;
        this.isDone = true;
    }
}
