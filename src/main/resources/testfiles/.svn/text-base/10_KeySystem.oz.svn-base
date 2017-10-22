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

class KeySystem{

    // all features visible
    
    state{
        keys: !P Key;
        rooms: !P Room;
        forall k:keys @ k.rooms <<= rooms;
    }
    
    INIT{
        forall k:keys @ k.INIT;
        forall r:rooms @ r.INIT;
    }
    extendAccess ^= [ k?:keys; ] @ k?.extendAccess

    rescindAccess ^= [ k?:keys; ] @ k?.rescindAccess
    
    /*    
    lock ^= ([]) r : rooms @ r.lock
    insertKey ^= [ r?:rooms; k?:keys ;] @
                        r?.supplyId
                        0/9
                        (k?.accessGranted && r?.unlock
                         []
                         k?.accessDenied)
    */
}
