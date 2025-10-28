package borealiscards.actions;

import borealiscards.vfx.LivingBladeEffect;
import borealiscards.vfx.LivingWindEffect;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.UUID;

public class LivingBladeAction extends AbstractGameAction {
    private final boolean freeToPlayOnce;
    private final int damage;
    private final AbstractPlayer p;
    private final AbstractMonster m;
    private final int magic;
    private final DamageInfo.DamageType damageTypeForTurn;
    private final int energyOnUse;
    private final UUID uuid;

    public LivingBladeAction(UUID targetUUID, AbstractPlayer p, AbstractMonster m, int magic, int damage, DamageInfo.DamageType damageTypeForTurn, boolean freeToPlayOnce, int energyOnUse) {
        this.p = p;
        this.m = m;
        this.magic = magic;
        this.damage = damage;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.damageTypeForTurn = damageTypeForTurn;
        this.energyOnUse = energyOnUse;
        this.uuid = targetUUID;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (energyOnUse != -1) {
            effect = energyOnUse;
        }

        if (p.hasRelic("Chemical X")) {
            effect += 2;
            p.getRelic("Chemical X").flash();
        }

        if (magic > 0) {
            for(int i = 0; i < magic; ++i) {
                Color livingColor = new Color(MathUtils.random(0.6F)+0.4F, MathUtils.random(0.6F)+0.4F, MathUtils.random(0.6F)+0.4F, 1.0F);
                if (i == 0) {
                    this.addToBot(new SFXAction("ATTACK_WHIRLWIND"));
                }
                this.addToBot(new VFXAction(new LivingWindEffect(livingColor.cpy(), MathUtils.randomBoolean())));
                addToBot(new LivingBladeEffect(m, livingColor.cpy()));
                addToBot(new LivingBladeDamageAction(m, new DamageInfo(p, damage, damageTypeForTurn)));
            }

            if (!freeToPlayOnce) {
                p.energy.use(EnergyPanel.totalCount);
            }
        }

        for(AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.uuid.equals(uuid)) {
                c.misc += effect;
                c.applyPowers();
                c.baseMagicNumber = c.misc;
            }
        }

        for(AbstractCard c : GetAllInBattleInstances.get(uuid)) {
            c.misc += effect;
            c.applyPowers();
            c.baseMagicNumber = c.misc;
        }

        this.isDone = true;
    }
}
