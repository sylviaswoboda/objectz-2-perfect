class CreditCard{
      visible(limit, balance, INIT, withdraw, deposit, withdrawAvail)

      const{
            limit: !N;
            limit in {1000, 2000, 5000};
      }

      state{
            balance: !Z;
            balance + limit >= 0;
      }

      INIT{
           balance = 0;
      }

      isValueSum{
           value1? : !N;
           value2? : !N;
           value1? > 0;
           value2? > 10;
           balance = value1? + value2?;
      }

      getBalanceVal{
           balanceVal! : !Z;
           balanceVal! = balance;
      }

      withdraw{
           delta(balance)
           amount?: !N;
           amount? <= balance + limit;
           balance' = balance - amount?;
      }

      deposit{
           delta(balance)
           amount?: !N;
           balance' = balance + amount?;
      }

      withdrawAvail{
           delta(balance)
           amount!: !N;
           amount! = balance + limit;
           balance' = -limit;
      }
}
class CreditCardConfirm{

    visible(limit, balance, INIT, withdraw, deposit, withdrawAvail, withdrawConfirm)

    inherits{
        CreditCard;
    }
      

    fundsAvail{
          funds! : !N;
          funds! = balance + limit;
    }

    withdrawConfirm ^= withdraw  0/9 fundsAvail

}


class GenCreditCards{
    visible( INIT, add, delete, withdraw, deposit, transferAvail, withdrawConfirm )

    state{
        cards: !P |v CreditCard;
    }

    INIT{
        cards = {};
    }

    add{
        delta(cards)
        card?: |v CreditCard;
        card? ~in cards;
        card?.INIT;
        cards' = cards ++ {card?};
    }

    delete{
        delta(cards)
        card?: |v CreditCard;
        card? in cards;
        cards' = cards \ {card?};
    }

    withdraw ^= [ card2?: cards; ] @ card2?.withdraw

    deposit ^= [ card?: cards; ] @ card?.deposit

    withdrawConfirm ^= [ card?: CreditCardConfirm; | card? in cards ] @
                            card?.withdrawConfirm
                            
/*
    transferAvail ^= [ from?, to?: cards; | from? ~= to? ] @
                        from?.withdrawAvail || to?.deposit
*/

}
