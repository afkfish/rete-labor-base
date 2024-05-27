package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class TrainSensorTest {
    TrainSensorImpl sensor;
    TrainController controller;
    TrainUser user;

    @Before
    public void before() {
        controller = mock(TrainController.class);
        user = mock(TrainUser.class);
        when(controller.getReferenceSpeed()).thenReturn(100);

        sensor = new TrainSensorImpl(controller, user);
    }

    @Test
    public void SpeedLimitUnderZeroTest() {
        sensor.overrideSpeedLimit(-1);
        verify(user, times(1)).setAlarmState(true);
    }

    @Test
    public void SpeedLimitOverFiveHundredTest() {
        sensor.overrideSpeedLimit(501);
        verify(user, times(1)).setAlarmState(true);

    }

    @Test
    public void SpeedLimitHalfOfReferenceSpeedTest() {
        sensor.overrideSpeedLimit(50);
        verify(user, never()).setAlarmState(true);

    }

    @Test
    public void SpeedLimitBetweenZeroAndFiveHundredAndUnderHalfOfReferenceSpeedTest() {
        sensor.overrideSpeedLimit(2);
        verify(user, times(1)).setAlarmState(true);
    }
}
