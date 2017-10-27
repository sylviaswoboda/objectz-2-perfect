[String]

class Book{
      visible(lend, return, latestRecension, authorList, setAuthorList)
      const{
            maxHistoryEntries: !N;
            maxHistoryEntries in {10, 20, 50, 100};
      }
      state{
      	authors, recensionHistory, readerHistory: seq String;
      	lent: !B;
      	delta {
      		totalLendingCount: !N;
      	}
      	#recensionHistory <= #readerHistory;
      	#recensionHistory <= maxHistoryEntries;
      	totalLendingCount = #readerHistory;
      	authors ~= [];
      }
    INIT{
        recensionHistory = [];
        readerHistory = [];
        ~lent;
    }
    authorList {
        authors!: seq String;
        authors! = authors;
    }
    setAuthorList {
        delta(authors)
        authors?: seq String;
        authors' = authors?;
    }
    latestRecension{
        recension!: String;
        #recensionHistory > 0;
        recension! = last recensionHistory;
    }
    lend{
    	delta(readerHistory, lent)
    	reader?:String;
      	~lent;
      	lent';
      	readerHistory' = readerHistory +^+ [reader?];
    }
}

class SmallLibrary {
    visible(postRecension, authorLists, transferAuthorsAndRecension, switchAuthorsAndLend)

	state {
		book1, book2, book3: Book;
		recensionsWall: Book <--> String;
		book1 ~= book2;
		book1 ~= book3;
		book2 ~= book3;
	}
	
	postRecension{
		delta(recensionsWall)
		book?: Book;
		recension?: String;
		recensionsWall' = recensionsWall >O< {book? |-> recension?};
	}
	
	authorLists ^= book1.authorList && book2.authorList;
	
	transferAuthorsAndRecension ^= (book1.authorList ||! book2.setAuthorList && book2.latestRecension) 0/9 book3.latestRecension;
	
	switchAuthorsAndLend ^= ((book1.authorList || book2.setAuthorList) && book2.lend) []
				((book2.authorList || book1.setAuthorList) && book2.lend)
}