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

class TwoCards{
      visible(totalbal, INIT, withdraw1, transfer, withdrawEither, replaceCard1, transferAvail)
      
      state{
            c1, c2: CreditCard;
            delta{
                  totalbal: !Z;
            }
            c1 ~= c2;
            totalbal = c1.balance + c2.balance;
      }
      
      INIT{
           c1.INIT;
           c2.INIT;
      }

      withdraw1 ^= c1.withdraw
      
      transfer ^= c1.withdraw && c2.deposit
    
      withdrawEither ^= c1.withdraw [] c2.withdraw

      replaceCard1{
           delta(c1)
	// Declaration part
           card?: CreditCard;

	// Predicate part
		// Preconditions
           card? ~in {c1, c2};
           card?.limit = c1.limit;
           card?.balance = c1.balance;
		// Postconditions
           c1' = card?;
      }

      transferAvail ^= c1.withdrawAvail || c2.deposit

}

