class CreditCard{
      visible(limit, balance, INIT, withdraw, deposit, withdrawAvail)

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
class CreditCardCount{
    visible(limit, balance, INIT, withdraw, deposit, withdrawAvail)

    inherits{
        CreditCard;
    }

    state{
        withdrawals: !N;
    }
    
    INIT{
        withdrawals = 0;
    }
    
    
    /*
     * extends the inherited withdraw schema
     */
    withdraw{
        delta(withdrawals)
        withdrawals' = withdrawals + 1;
    }


}