package borealiscards.cards.watcher;

import borealiscards.cards.BaseCard;
import borealiscards.util.CardStats;
import borealiscards.vfx.StarshowerEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Starshower extends BaseCard {
    public static final String ID = makeID(Starshower.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.PURPLE,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ALL_ENEMY,
            2
    );

    public Starshower() {
        super(ID, info);
        setDamage(5);
        setSelfRetain(false,true);
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new StarshowerEffect(AbstractDungeon.player.hand.size(), AbstractDungeon.getMonsters().shouldFlipVfx()), 0.25F));
        for(int i = 0; i < AbstractDungeon.player.hand.size(); ++i) {
            addToBot(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_LIGHT, true));
        }
    }
}
