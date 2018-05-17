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
import Operator.CheckOperator;
import Model.Game;
import Model.Player;
import Model.Rank;
import Model.Suit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author adam
 */
public class CheckOperatorTest {

    CheckOperator checkoperator;
    Game game;

    public CheckOperatorTest() {
    }

    private void initTable(Card... cards) {
        for (Card card : cards) {
            game.getTable().addCard(card);
        }
    }

    @Before
    public void setUp() {
        checkoperator = CheckOperator.getCheckoperator();
        game = new Game();
        game.start();
    }

    @Test
    public void canCheck() {
        initTable(new Card(Rank.also, Suit.zold), new Card(Rank.het, Suit.makk));
        assertTrue(checkoperator.isApplicable(game));
    }
    
    @Test
    public void canCheck2() {
        initTable(new Card(Rank.felso, Suit.piros), new Card(Rank.felso, Suit.zold));
        assertTrue(checkoperator.isApplicable(game));
    }
    
    @Test
    public void emptyTable() {
        assertFalse(checkoperator.isApplicable(game));
    }
    
    @Test
    public void cantCheck() {
        initTable(new Card(Rank.felso, Suit.makk));
        assertFalse(checkoperator.isApplicable(game));
    }
    
    @Test
    public void cantCheck2() {
        initTable(new Card(Rank.kiraly, Suit.makk));
        assertFalse(checkoperator.isApplicable(game));
    }
    
    @Test
    public void check() {
        Player nextPlayer = game.getNextplayer();
        checkoperator.apply(game);
        assertSame(nextPlayer, game.getCurrentplayer());        
    }

    @After
    public void tearDown() {
    }

}

