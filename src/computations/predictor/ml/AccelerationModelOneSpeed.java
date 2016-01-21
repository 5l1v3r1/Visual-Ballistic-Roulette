package computations.predictor.ml;

import computations.wheel.Type;

//We cannot constructor an acceleration speed model with just one speed.
//So we create an object to emulate this model, which returns the last wheel speed.
class AccelerationModelOneSpeed extends AccelerationModel
{
	private double lastSpeed;

	@Override
	public String toString()
	{
		return "AccelerationModelOneSpeed [lastSpeed=" + lastSpeed + ", slope=" + slope + ", intercept=" + intercept + ", "
				+ (type != null ? "type=" + type : "") + "]";
	}

	@Override
	public double estimateSpeed(double time)
	{
		return lastSpeed;
	}

	AccelerationModelOneSpeed(Type type, double lastSpeed)
	{
		super(0, 0, type);
		this.lastSpeed = lastSpeed;
	}

}