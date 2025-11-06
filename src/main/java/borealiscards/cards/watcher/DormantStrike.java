package borealiscards.cards.watcher;

import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DormantStrike extends BaseCard {
    public static final String ID = makeID(DormantStrike.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.PURPLE,
            CardType.ATTACK,
            CustomRarity.SHOP,
            CardTarget.ENEMY,
            1
    );

    public DormantStrike() {
        super(ID, info);
        setDamage(11,4);
        setSelfRetain(true);
        setExhaust(true);
    }

    public void onRetained() {
        DormantStrikeCopy created = new DormantStrikeCopy();
        if (upgraded) {
            created.upgrade();
        }
        addToBot(new MakeTempCardInHandAction(created));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }
}
