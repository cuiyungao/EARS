/**
 * Generic data about rating Player. In case of rating algorithms player is specific algorithm, with id = versionAcronym.  
 * <p>
 * 
 * @author Matej Crepinsek
 * @version 1
 * 
 *          <h3>License</h3>
 * 
 *          Copyright (c) 2011 by Matej Crepinsek. <br>
 *          All rights reserved. <br>
 * 
 *          <p>
 *          Redistribution and use in source and binary forms, with or without
 *          modification, are permitted provided that the following conditions
 *          are met:
 *          <ul>
 *          <li>Redistributions of source code must retain the above copyright
 *          notice, this list of conditions and the following disclaimer.
 *          <li>Redistributions in binary form must reproduce the above
 *          copyright notice, this list of conditions and the following
 *          disclaimer in the documentation and/or other materials provided with
 *          the distribution.
 *          <li>Neither the name of the copyright owners, their employers, nor
 *          the names of its contributors may be used to endorse or promote
 *          products derived from this software without specific prior written
 *          permission.
 *          </ul>
 *          <p>
 *          THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *          "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *          LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 *          FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 *          COPYRIGHT OWNERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 *          INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 *          BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *          LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 *          CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 *          LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 *          ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *          POSSIBILITY OF SUCH DAMAGE.
 * 
 */
package org.um.feri.ears.rating;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import org.um.feri.ears.benchmark.AlgorithmEvalResult;

public class Player {
    protected String playerId; // name
    protected Rating r; // current ration
    protected ArrayList<Game> listOfGamePlayed; // in last period (not evaluated
    protected WinLossDraw  sumWinLossDraw;                                         // yet)
    public HashMap<String,WinLossDraw> wldPlayers; //id is algorithm
    public HashMap<String,WinLossDraw> wldProblems; //id is problem

    public Player(String playerId, Rating r, int w, int l, int d) {
        super();
        wldPlayers = new HashMap<String, WinLossDraw>();
        wldProblems = new HashMap<String, WinLossDraw>();
        sumWinLossDraw = new WinLossDraw(w, l, d);
        this.playerId = playerId;
        this.r = r;
        listOfGamePlayed = new ArrayList<Game>();
    }

    public Player(String playerId) {
        this(playerId, new Rating(1500, 350, 0.06),0,0,0); // default from org. paper
    }

    /**
     * Adds new game
     * 
     * @param newone
     */
    public void add(Game newone) {
        WinLossDraw tmpPlayer = wldPlayers.get(newone.getOpponent(playerId));
        WinLossDraw tmpProblem = wldProblems.get(newone.getIdProblem());
        if (tmpPlayer==null) {
            tmpPlayer = new WinLossDraw(0,0,0);
            wldPlayers.put(newone.getOpponent(playerId), tmpPlayer);
        }
        if (tmpProblem==null) {
            tmpProblem = new WinLossDraw(0,0,0);
            wldProblems.put(newone.getIdProblem(), tmpProblem);
        }
        if (newone.getGameResult(playerId) == Game.DRAW) {
            sumWinLossDraw.incDraw();
            tmpPlayer.incDraw();
            tmpProblem.incDraw();
        }
        else if (newone.getGameResult(playerId) == Game.WIN) {
            sumWinLossDraw.incWin();
            tmpPlayer.incWin();
            tmpProblem.incWin();
        }
        else {
            sumWinLossDraw.incLoss();
            tmpPlayer.incLoss();
            tmpProblem.incLoss();
        }
        listOfGamePlayed.add(newone);

    }

    public ArrayList<Game> getUnEvaluatedGames() {
        return listOfGamePlayed;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public Rating getR() {
        return r;
    }

    /**
     * New rating is calculated. listOfGamePlayed is deleted!
     * 
     * @param r
     */
    public void setR(Rating r) {
        listOfGamePlayed.clear();
        this.r = r;
    }
    public WinLossDraw getSumWinLossDraw() {
        return sumWinLossDraw;
    }
    public String toString() {
        return playerId + "; " + r +sumWinLossDraw+"\n\t Against:"+wldPlayers+"\n\t Problems:"+wldProblems;
    }
    
    public static class RatingComparator implements Comparator<Player> {
        @Override
        public int compare(Player a, Player b) {
            if (a.r.getRating()>b.r.getRating()) return -1;
            if (a.r.getRating()<b.r.getRating()) return 1;
            return 0;
        }
    }


}
