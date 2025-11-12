package borealiscards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.UUID;

public class IronhandAction extends AbstractGameAction {
    private final UUID uuid;

    public IronhandAction(UUID targetUUID) {
        this.uuid = targetUUID;
    }

    public void update() {

        for(AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.uuid.equals(uuid)) {
                c.misc += 2;
                c.applyPowers();
                c.baseDamage = c.misc;
            }
        }

        for(AbstractCard c : GetAllInBattleInstances.get(uuid)) {
            c.misc += 2;
            c.applyPowers();
            c.baseDamage = c.misc;
        }

        this.isDone = true;
    }
}
