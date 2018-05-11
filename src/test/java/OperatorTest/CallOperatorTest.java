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

import Operator.CallOperator;
import Model.Card;
import Model.Game;
import Model.Suit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author adam
 */
public class CallOperatorTest {

    private Game game;
    private CallOperator calloperator;

    public CallOperatorTest() {
    }

    private void initPlayer() {
        Card[] cards = new Card[]{new Card(Model.Rank.also, Suit.tok),
            new Card(Model.Rank.also, Suit.zold),
            new Card(Model.Rank.felso, Suit.tok),
            new Card(Model.Rank.tiz, Suit.piros)};
        List<Card> inHand = new ArrayList<>(Arrays.asList(cards));
        game.getCurrentplayer().addCards(inHand);

    }

    private void initTable(Card... cards) {
        for (Card card : cards) {
            game.getTable().addCard(card);
        }
    }

    @Before
    public void setUp() {
        game = new Game();
        calloperator = CallOperator.getCallOperator();
        initPlayer();
    }

    @Test
    public void testEmptyTable() {
        assertTrue(calloperator.isApplicable(game));
    }

    @Test
    public void firstCall() {
        assertTrue(calloperator.isApplicable(game));
    }

    @Test
    public void canHold() {
        initTable(new Card(Model.Rank.also, Suit.zold), new Card(Model.Rank.het, Suit.makk));
        assertTrue(calloperator.isApplicable(game));
    }

    @Test
    public void cantHold() {
        initTable(new Card(Model.Rank.kiraly, Suit.piros), new Card(Model.Rank.kiraly, Suit.makk));
        assertFalse(calloperator.isApplicable(game));
    }
    
    @Test
    public void applyCall() {
        game.getCurrentplayer().setChoosedCard(game.getCurrentplayer().cards.get(0));
        calloperator.apply(game);
        assertFalse(game.getTable().getCards().isEmpty());
    }

    @After
    public void tearDown() {
    }

}

