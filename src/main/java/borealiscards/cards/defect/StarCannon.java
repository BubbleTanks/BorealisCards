package borealiscards.cards.defect;

import borealiscards.cards.BaseCard;
import borealiscards.orbs.Starlight;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

public class StarCannon extends BaseCard {
    public static final String ID = makeID(StarCannon.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            2
    );

    public StarCannon() {
        super(ID, info);
        setDamage(5,2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChannelAction(new Starlight()));
        addToBot(new ChannelAction(new Starlight()));

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractOrb o : AbstractDungeon.player.orbs) {
                    if (o.ID == Starlight.ORB_ID) {
                        this.addToBot(new SFXAction("ATTACK_HEAVY"));
                        this.addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
                        addToBot(new DamageAllEnemiesAction(p, damage, damageTypeForTurn, AttackEffect.NONE));
                    }
                }
                this.isDone = true;
            }
        });

    }
}
