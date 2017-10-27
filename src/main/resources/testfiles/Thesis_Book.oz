[String]

class Book{
      visible(lend, return, review, latestRecension, authorList, readerList, setAuthorList)
      const{
            maxHistoryEntries: !N;
            maxHistoryEntries in {10, 20, 50, 100};
      }
      state{
      	title: String;
      	authors, recensionHistory, readerHistory: seq String;
      	lent, reviewed, mayBeReviewed: !B;
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
        ~lent and ~reviewed and ~mayBeReviewed;
    }
    lend{
    	delta(mayBeReviewed, readerHistory, lent, reviewed)
    	reader?:String;
      	~lent;
      	mayBeReviewed' and lent' and ~reviewed';
      	readerHistory' = readerHistory +^+ [reader?];
    }
    return{
        delta(lent, mayBeReviewed)
        reader?: String;
    	lent;
    	reader? = last readerHistory;
    	~mayBeReviewed' and ~lent';
    }
    latestRecension{
        recension!: String;
        reviewed;
        recension! = last recensionHistory;
    }
    readerList {
        readers!: seq String;
        readers! = readerHistory;
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
    review {
    	delta(reviewed, recensionHistory, mayBeReviewed)
    	recension?: String;
    	reader?: String;
    	~reviewed and mayBeReviewed;
    	reader? = last readerHistory;
    	reviewed' and ~mayBeReviewed';
    	((#recensionHistory = maxHistoryEntries and recensionHistory' = tail recensionHistory +^+ [recension?]) or
    	 (#recensionHistory < maxHistoryEntries and recensionHistory' = recensionHistory +^+ [recension?]))
    }
}

class SmallLibrary {
    visible(postRecension, lendBook1, lendBook2, returnBook1, returnBook2, lendBoth, returnBoth, transferAuthors, transferAuthorsAssoc, lendAny, returnAny, lendAndReaderList1, reviewAndReturnBook2, switchAuthorsAndLend)

	state {
		book1, book2: Book;
		recensionsWall: Book <--> String;
		delta {
			books: !P Book;
		}
		book1 ~= book2;
		books = {book1} ++ {book2};
	}
	INIT {
		recensionsWall = {};
	}
	postRecension{
		delta(recensionsWall)
		book?: Book;
		recension?: String;
		recensionsWall' = recensionsWall >O< {book? |-> recension?};
	}
	
	lendBook1 ^= book1.lend;
	lendBook2 ^= book2.lend;
	returnBook1 ^= book1.return;
	returnBook2 ^= book2.return;

	lendBoth ^= book1.lend && book2.lend;
	returnBoth ^= book1.return && book2.return;
	
	transferAuthors ^= book1.authorList || book2.setAuthorList;
	transferAuthorsAssoc ^= book1.authorList ||! book2.setAuthorList;
	
	lendAny ^= book1.lend [] book2.lend;
	returnAny ^= book1.return [] book2.return;
	
	lendAndReaderList1 ^= book1.lend 0/9 book1.readerList
	reviewAndReturnBook2 ^= book2.review 0/9 book2.return;
	
	switchAuthorsAndLend ^= ((book1.authorList || book2.setAuthorList) && book2.lend) []
				((book2.authorList || book1.setAuthorList) && book2.lend)
}