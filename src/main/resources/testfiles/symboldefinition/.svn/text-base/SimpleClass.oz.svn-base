class SimpleClass {
      visible(limit, balance, INIT, withdraw, deposit, withdrawAvail)

      const{
            limit: !N;
            limit in {1000, 2000, 5000};
      }

      state{
            balance: !Z;
            exists x:limit @ x > balance;
            limit > balance;
      }

      INIT{
           balance = 0;
      }

      aBoolFunction{
           value1? : !N;
           value2? : !N;
           house? : House;
           value1? > 0;
           value2? > 10;
           balance = value1? + value2?;
      }

      aFunction{
           balanceVal! : !Z;
           balanceVal! = balance;
      }

      aSchema{
           delta(balance)
           amount?: !N;
           amount? <= balance + limit;
           balance' = balance - amount?;
      }
}