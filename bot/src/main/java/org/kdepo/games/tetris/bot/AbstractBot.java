package org.kdepo.games.tetris.bot;

import org.kdepo.games.tetris.bot.model.BotAction;
import org.kdepo.games.tetris.bot.model.BotThink;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBot implements BotThink {

    /**
     * Actions generated when bot think
     */
    protected List<BotAction> botActionList;

    public AbstractBot() {
        botActionList = new ArrayList<>();
    }

    /**
     * Returns action to be applied on a current figure. If no action returned then {@link BotAction#DROP} will be applied.
     *
     * @return action to be applied on a current figure
     */
    public BotAction getNextAction() {
        BotAction nextAction = null;
        if (!botActionList.isEmpty()) {
            nextAction = botActionList.get(0);
            botActionList.remove(0);
        }
        return nextAction;
    }
}
