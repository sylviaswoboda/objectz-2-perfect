
Posn == 0 .. 8


const{
    inLine: (!P Posn) ---> !B;
/*
    forall ps: !P Posn @ inLine(ps) <=>
            (exists s: { {0,1,2}, {3,4,5}, {6,7,8},
                        {0,3,6}, {1,4,7}, {2,5,8},
                        {0,4,8}, {2,4,6}} @ s <<= ps)
                        
*/
}
/*
Result ::= black_wins | white_wins | draw

class PassiveBoard{
    state{
        bposn, wposn: !P Posn;
    }

    INIT{
        bposn = {};
        wposn = {};
    }

    addToBlack{
        delta(bposn)
        p?: Posn;
        bposn' = bposn ++ {p?};
    }

    addToWhite{
        delta(wposn)
        p?: Posn;
        wposn' = wposn ++ {p?};
    }
}

class ActiveBoard{

    Colour ::= black | white
    
    state{
        bposn, wposn: !P Posn;
        turn: Colour;
        delta{
            free: !P Posn;
            isOver: !B;
        }
        bposn ** wposn = {};
        free = Posn \ ( bposn ++ wposn);
        isOver <=> true;

        isOver <=> inLine(bposn) or inLine(wposn) or free = {};

    }
    INIT{
        bposn = {} and wposn = {};
    }

    addToBlack{
        delta(bposn, turn)
        p?: Posn;
        turn = black;
        ~ isOver;
        p? in free;
        bposn' = bposn ++ {p?};
        turn' = white;
    }

    addToWhite{
        delta(wposn, turn)
        p?: Posn;
        turn = white;
        ~ isOver;
        p? in free;
        wposn' = wposn ++ {p?};
        turn' = black;
    }

    rejectBlack{
        p?: Posn;
        (turn = white) or isOver or (p? in free);
    }

    rejectWhite{
        p?: Posn;
        (turn = black) or isOver or (p? in free);
    }

    considerBlack ^= addToBlack [] rejectBlack

    considerWhite ^= addToWhite [] rejectWhite

    gameResult{
        result!: Result;
        isOver;
        inLine(bposn) => result! = black_wins;
        inLine(wposn) => result! = white_wins;
        ~ inLine(bposn) and ~ inLine(wposn) => result! = draw;
    }
}
*/