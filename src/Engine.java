import java.awt.geom.Point2D;

	public class Engine implements BallListener{
    private GameState state;
    public Engine(GameState state)
	{
        this.state = state;
    }

    public void tick()
	{

    }
	public void speed(Ball regballs)
	{
		float newX = (float)regballs.getLocation().getX();
		float newY = (float)regballs.getLocation().getY() + regballs.getSpeed();
		regballs.setLocation(wall((int) regballs.getSpeed(), (int) regballs.getAngle(), regballs.getLocation()));
	}
	public Point2D wall(int rise, int run, Point2D location)
	{
		Point2D temploc = new Point2D.Float((float)location .getX() + run, (float)location.getY() + rise);
		double X;
		double Y;
		Point2D wall = null;
			if (location.getX() > 300 || location.getX() < 0 || location.getY() > 700 || location.getY() < 0)
			{
				X = location.getX();
				Y = location.getY();
				wall = new Point2D.Float((float)X - run, (float)Y - rise);
			}
			for (int ii = 0; ii < state.getMyBalls().length; ii++)
			{
				if (state.getMyBalls()[ii].isOnBall(location))
				{
					return new Point2D.Float((float)location.getX() - run, (float)location.getY() - rise);
				}
			}
			for (int iii = 0; iii < state.getTheirBalls().length; iii++)
			{
				if (state.getTheirBalls()[iii].isOnBall(location))
				{
					return new Point2D.Float((float)location.getX() - run, (float)location.getY() - rise);
				}
			}
			location.setLocation(location.getX() + run, location.getY() + rise);

		return wall;
	}

    // BallListener implementation
	@Override
    public void ballSentIntoMotion(Ball b, float speed, float angle)
	{
		b.setAngle(angle);
		b.setSpeed(speed);
    }
    public void ballRelocated(Ball b, Point2D p) {
		b.setLocation(p);
    }
    public void ballImpacted(Ball a, Ball b, Point2D impactPoint)
	{
		if (a.getSpeed() > b.getSpeed())
		{
			ballSentIntoMotion(a, getImpactSpeed(a, b, true), a.getAngle());
			ballSentIntoMotion(b, getImpactSpeed(a, b, false), b.getAngle());
		}
		else if (a.getSpeed() < b.getSpeed())
		{
			ballSentIntoMotion(a, getImpactSpeed(a, b, false), a.getAngle());
			ballSentIntoMotion(b, getImpactSpeed(a, b, true), b.getAngle());
		}
    }

	public float getImpactSpeed(Ball a, Ball b, boolean forMovingBall) {
		if (forMovingBall)
			return (float) (Math.max(a.getSpeed(), b.getSpeed()) * 0.75);
		else
			return (float) (Math.max(a.getSpeed(), b.getSpeed()) * 0.5);
	}
}
/*
What happens when the cue ball hits the carpet balls.
What happens when the carpet balls hit other carpet balls.
What happens When the balls hit the wall.
What happens to the ball when it gets thrown.
How much power the ball gets thrown with.
How much spin the ball has.
The angle the ball gets thrown.
What happens when the ball reaches the end of the board."Set velocity to 0"
 */