class CreditCard{

      const{
            limit: !N;
            limit in {1000, 2000, 5000};
      }

      state{
            balance: !Z;
            limit > balance;
      }

      INIT{
           balance = 0;
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
class CreditCards{

      visible(commonlimit, INIT, add, delete, withdraw, deposit, withdrawAvail, transferAvail)

      const{
            commonlimit: !N;
            commonlimit in {1000, 2000, 5000};
      }

      state{
            cards: !P CreditCard;
            forall c: cards @ c.limit = commonlimit;
      }

      INIT{
           cards = {};
      }


      add{
          delta(cards)
          card?: CreditCard;
          card? ~in cards;
          card?.limit = commonlimit;
          card?.INIT;
          cards' = cards ++ {card?};
      }

      delete{
          delta(cards)
          card?: CreditCard;
          card? in cards;
          cards' = cards \ {card?};
      }

      withdraw ^= [ card?: cards; ] @ card?.withdraw

      deposit ^= [ card?: cards; ] @ card?.deposit

      withdrawAvail ^= [ card?: cards; ] @ card?.withdrawAvail

      transferAvail ^= [ from?, to?: cards; | from? ~= to?;] @ from?.withdrawAvail || to?.deposit

}