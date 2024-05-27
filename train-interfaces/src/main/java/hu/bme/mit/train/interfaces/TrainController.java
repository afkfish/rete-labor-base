package hu.bme.mit.train.interfaces;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.Date;

public interface TrainController {
	Table<Date, Integer, Integer> tachograph = HashBasedTable.create();

	void startTrainTickThread(int tickInterval);

	void stopTrainTickThread();

	void followSpeed();

	int getReferenceSpeed();

	void setSpeedLimit(int speedLimit);

	void setJoystickPosition(int joystickPosition);

	void setEmergencyBrake(boolean emergencyBrake);

	void storeTachograph();

}
