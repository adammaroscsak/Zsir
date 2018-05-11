/* 
 * Copyright (C) 2018 Adam
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Operator;

import Model.Card;
import Model.Game;
import Model.Rank;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A model class for the beat operator.
 *
 * The beat operator removes the cards from the table and computes the score.
 *
 * @author adam
 */
public class BeatOperator implements Operator {

    private static final Logger logger = LoggerFactory.getLogger(BeatOperator.class);

    /**
     * The beat operator.
     */
    private static final BeatOperator beatoperator = new BeatOperator();

    /**
     * Indicates whether the beat operator is applicable.
     *
     * @param game the state of the game
     * @return true if the beat operator is applicable
     */
    @Override
    public boolean isApplicable(Game game) {
        return game.getNextplayer().isChecked() 
                || (game.getTable().getCards().size() > 0 && game.getTable().getCards().size() % 2 == 0 
                && (game.getTable().getBottomCard().getRank() != game.getTable().getTopCard().getRank()
                && game.getTable().getTopCard().getRank() != Rank.het));
    }

    /**
     * Applies the beat operator on the specified state of the
     * <code>game</code>.
     *
     * @param game the state of the game
     */
    @Override
    public void apply(Game game) {
        logger.trace("Apply beat operator.");
        game.getCurrentplayer().addScore(computeScore(game.getTable().getCards()));
        game.getNextplayer().setCheck(false);
        game.getTable().clear();
        if (DrawOperator.getDrawoperator().isApplicable(game)) {
            DrawOperator.getDrawoperator().apply(game);
        }
    }

    /**
     * Gets the beat operator.
     *
     * @return the beat operator
     */
    public static BeatOperator getBeatoperator() {
        return beatoperator;
    }

    /**
     * Computes the score.
     *
     * @param cards the cards on the table
     * @return the score
     */
    private int computeScore(List<Card> cards) {
        int score = 0;
        score = cards.stream().filter((card) -> (card.getRank() == Rank.asz || 
                card.getRank() == Rank.tiz))
                .map((_item) -> 1).reduce(score, Integer::sum);
        return score;
    }

    /**
     * Gets the string representation of the beat operator.
     *
     * @return the string representation of the beat operator
     */
    @Override
    public String toString() {
        return "BeatOperator";
    }

}

