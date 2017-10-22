class Room{

    // all features visible
    
    state{
        locked : !B;
    }
    
    INIT{
        locked;
    }
    
    supplyId{
        rm!: Room;
        rm! = self;
    }
    
    unlock{
        delta(locked)
        locked;
        ~locked';
    }
    
    lock{
        delta(locked)
        ~locked;
        locked';
    }
}

class Key{

    // no visibility list means all features are visible

    state{
        rooms: !P Room;
    }

    INIT{
        rooms = {};
    }

    extendAccess{
        delta(rooms)
        rm?: Room;
        rm? ~in rooms;
        rooms' = rooms ++ {rm?};
    }

    rescindAccess{
        delta(rooms)
        rm?: Room;
        rm? in rooms;
        rooms' = rooms \ {rm?}
    }
    
    accessGranted{
        rm?: Room;
        rm? in rooms;
    }
    
    accessDenied{
        rm?: Room;
        rm? ~in rooms;
    }
}


class DataBase{

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
