[String]

class Book{
      visible(lend, return, review, latestRecension, authorList, readerList, setAuthorList)

      const{
            maxHistoryEntries: !N;
            maxHistoryEntries in {10, 20, 50, 100};
      }

      state{
      	title: String;
      	authors: seq String;
      	recensionHistory: seq String;
      	readerHistory: seq String;
      	lent: !B;
      	reviewed: !B;
      	mayBeReviewed: !B;
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
	      ~reviewed;
	      ~mayBeReviewed;
    }
	lend{
    	delta(mayBeReviewed, readerHistory, lent, reviewed)
    	lender?:String;
      	~lent;
      	mayBeReviewed';
      	lent';
      	~reviewed';
      	readerHistory' = readerHistory +^+ [lender?];
    }

	return{
    	delta(lent, mayBeReviewed)
    	lender?: String;
    	
    	lent;
    	lender? = last readerHistory;
    	~mayBeReviewed';
    	~lent';
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
    	
    	~reviewed;
    	reader? = last readerHistory;
    	mayBeReviewed;
    	reviewed';
    	~mayBeReviewed';
    	((#recensionHistory = maxHistoryEntries and recensionHistory' = tail recensionHistory +^+ [recension?]) or
    	 (#recensionHistory < maxHistoryEntries and recensionHistory' = recensionHistory +^+ [recension?]))
    }
}

class SmallLibrary {

	visible(postRecension, lendBook1, lendBook2, returnBook1, returnBook2, lendBoth, returnBoth,
	        transferAuthors, transferAuthorsAssoc, lendAny, returnAny, lendAndReaderList1, reviewAndReturnBook2)

	state {
		book1: Book;
		book2: Book;
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

}