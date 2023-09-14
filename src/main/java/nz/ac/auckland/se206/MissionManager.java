package nz.ac.auckland.se206;

import java.util.HashMap;
import nz.ac.auckland.se206.missions.Mission;
import nz.ac.auckland.se206.missions.WindowMission;

public class MissionManager {
  public enum MISSION {
    WINDOW,
  }

  private HashMap<MISSION, Mission> missionList = new HashMap<>();

  public void addMission(int missionNumber) {
    if (missionNumber == 1) {
      missionList.put(MISSION.WINDOW, new WindowMission());
    }
  }
}
