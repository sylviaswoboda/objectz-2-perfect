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
}
class CreditCards{

      visible(withdraw, withdrawAux, withdrawAuxSet)

      state{
      		c1: CreditCard;
      		c2: CreditCard;
            cards: !P CreditCard;
      }

      withdraw ^= [ card?: cards; ] @ card?.withdraw 
      withdrawAux ^= [ card: cards; ] @ card.withdraw
      withdrawAuxSet ^= [ card: {c1, c2}; ] @ card.withdraw
}