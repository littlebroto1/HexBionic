package io.github.littlebroto1.hexbionics.entity;

import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class ReplacedPlayerEntity implements IAnimatable {
    protected static final AnimationBuilder WALK_ANIM = new AnimationBuilder().addAnimation("animation.amethyst_golem.walk", ILoopType.EDefaultLoopTypes.LOOP);

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    @Override
    public void registerControllers(AnimationData animationData) {
        //animationData.addAnimationController(new AnimationController<>(this, "Walking", 0, this::walkAnimController));
    }

    protected <E extends ReplacedPlayerEntity> PlayState walkAnimController(final AnimationEvent<E> event) {
        if (!event.isMoving()) {
            event.getController().setAnimation(WALK_ANIM);
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
