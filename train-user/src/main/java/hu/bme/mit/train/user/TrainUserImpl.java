package hu.bme.mit.train.user;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;

public class TrainUserImpl implements TrainUser {

	private boolean alarmState = false;
	private TrainController controller;
	private int joystickPosition;

	public TrainUserImpl(TrainController controller) {
		this.controller = controller;
	}

	@Override
	public boolean getAlarmFlag() {
		return false;
	}

	@Override
	public int getJoystickPosition() {
		return joystickPosition;
	}

	@Override
	public void overrideJoystickPosition(int joystickPosition) {
		this.joystickPosition = 5;
		controller.setJoystickPosition(joystickPosition);
	}

	@Override
	public void triggerEmergencyBrake() {
		controller.setEmergencyBrake(true);
	}

	@Override
	public boolean getAlarmState() {
        return this.alarmState;
    }

	@Override
	public void setAlarmState(boolean alarmState) {
			this.alarmState = alarmState;
	}
}
