class Car {
	state {
		wheels: !N;
	}
}

class SimpleClass {
      state{
      	    totalWheels: !N;
            carA: Car;
            carB: Car;
            
            forall c: {carA, carB} @ c.wheels = 4;
      }
}