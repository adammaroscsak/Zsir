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
package ModelTest;

import Model.Card;
import Model.Rank;
import Model.Suit;
import Model.Table;
import org.junit.After;
import static org.junit.Assert.assertSame;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author adam
 */
public class TableTest {
    
    private Table table;
    
    private void initTable(Card card) {
        table.addCard(card);
    }
    
    @Before
    public void setUp() {
        table = new Table();
    }
    
    @Test
    public void topCardTest() {
        Card card = new Card(Rank.also, Suit.tok);
        initTable(card);
        assertSame(card, table.getTopCard());
    }
    
    @Test
    public void bottomCardTest() {
        Card cardOne = new Card(Rank.also, Suit.tok);
        Card cardTwo = new Card(Rank.tiz, Suit.tok);
        initTable(cardOne);
        initTable(cardTwo);
        assertSame(table.getBottomCard(), cardOne);
    }
    
    @After
    public void tearDown() {
    }
    
}

