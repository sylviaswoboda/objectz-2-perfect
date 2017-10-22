class DataBase{

	[Room, Key]

    state{
        access: Key <--> Room;
    }
    
    INIT{
        access = {};
        
    }
    
    extendAccess{
        delta(access)
        key?: Key;
        rm?: Room;
        // Check if the pair (key?, rm?) is already in the relation access
        ~ access(key?, rm?);
        access' = access ++ {key? |-> rm?};
    }

    rescindAccess{
        delta(access)
        key?: Key;
        rm?: Room;
        access(key?, rm?);
        access' = access \ {key? |-> rm? };
    }
    accessGranted{
        key?: Key;
        rm?: Room;
        access(key?, rm?);
    }

    accessDenied{
        key?: Key;
        rm?: Room;
        ~ access(key?, rm?);
    }
    
}
