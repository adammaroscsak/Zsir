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
package OperatorTest;

import Model.Card;
import Operator.BeatOperator;
import Model.Game;
import Model.Suit;
import Model.Rank;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author adam
 */
public class BeatOperatorTest {

    private BeatOperator beatoperator;
    private Game game;

    public BeatOperatorTest() {
    }

    private void initTable(Card... cards) {
        for (Card card : cards) {
            game.getTable().addCard(card);
        }
    }

    @Before
    public void setUp() {
        beatoperator = BeatOperator.getBeatoperator();
        game = new Game();
        game.start();
    }

    @Test
    public void canBeat() {
        game.getNextplayer().check();
        assertTrue(beatoperator.isApplicable(game));
    }

    @Test
    public void cantBeat() {
        assertFalse(beatoperator.isApplicable(game));
    }

    @Test
    public void applyBeat() {
        initTable();
        beatoperator.apply(game);
        assertTrue(game.getTable().getCards().isEmpty());
    }

    @Test
    public void scoreTest() {
        initTable(new Card(Rank.tiz, Suit.tok));
        beatoperator.apply(game);
        assertEquals(1, game.getCurrentplayer().getScore());
    }

    @Test
    public void scoreTest2() {
        initTable(new Card(Rank.felso, Suit.tok));
        beatoperator.apply(game);
        assertEquals(0, game.getCurrentplayer().getScore());
    }

    @After
    public void tearDown() {
    }

}

