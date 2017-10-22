[String]
class OverrideFunction{
	state{
		recensionWall: !N <--> String;
	}
	
	INIT{
		recensionWall = {};
	}
	postRecension{
		delta(recensionWall)
		index?: !N;
		recension?: String;
		recensionWall' = recensionWall >O< {index? |-> recension?};
	}
}