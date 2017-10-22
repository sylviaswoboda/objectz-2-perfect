class CreditCard {
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