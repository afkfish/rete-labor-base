package hu.bme.mit.train.controller;

import hu.bme.mit.train.interfaces.TrainController;

import java.util.Date;

public class TrainControllerImpl implements TrainController {

	private Thread trainTickThread;

	private int step = 0;
	private int referenceSpeed = 0;
	private int speedLimit = 0;

	public TrainControllerImpl() {
		startTrainTickThread(1000);
	}

	@Override
	public void startTrainTickThread(int tickInterval) throws IllegalArgumentException {
		if (trainTickThread != null) {
			throw new IllegalArgumentException("Thread is already running");
		}
		this.trainTickThread = new Thread(() -> {
			while (true) {
				synchronized(this) {
					followSpeed();
					storeTachograph();
				}

				try {
					Thread.sleep(tickInterval);
				} catch (InterruptedException e) {
					System.out.println("Thread interrupted");
				}
			}
		});
	}

	@Override
	public void stopTrainTickThread() {
		if (trainTickThread != null) {
			trainTickThread.interrupt();
			trainTickThread = null;
			System.out.println("Thread stopped");
		}
	}

	@Override
	synchronized public void followSpeed() {
		if (referenceSpeed < 0) {
			referenceSpeed = 0;
		} else {
			if (referenceSpeed + step > 0) {
				referenceSpeed += step;
			} else {
				referenceSpeed = 0;
			}
		}

		enforceSpeedLimit();
	}

	@Override
	public int getReferenceSpeed() {
		return referenceSpeed;
	}

	@Override
	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
		synchronized (this) {
			enforceSpeedLimit();
		}
	}

	synchronized private void enforceSpeedLimit() {
		if (referenceSpeed > speedLimit) {
			referenceSpeed = speedLimit;
		}
	}

	@Override
	public void setJoystickPosition(int joystickPosition) {
		this.step = joystickPosition;		
	}

	@Override
	synchronized public void setEmergencyBrake(boolean emergencyBrake) {
		referenceSpeed = 0;
	}

	@Override
	synchronized public void storeTachograph() {
		tachograph.put(new Date(), this.step, referenceSpeed);
	}

}
