package nz.ac.auckland.se206;

import java.util.ArrayList;
import java.util.HashMap;
import nz.ac.auckland.se206.missions.Mission;
import nz.ac.auckland.se206.missions.WindowMission;

public class MissionManager {
  public enum MISSION {
    WINDOW,
  }

  private HashMap<MISSION, Mission> missionList = new HashMap<>();
  private ArrayList<MISSION> keyList = new ArrayList<>();

  public void addMission(int missionNumber) {
    if (missionNumber == 1) {
      missionList.put(MISSION.WINDOW, new WindowMission());
      keyList.add(MISSION.WINDOW);
    }
  }

  public int getOverallPercentage() {
    int percentage = 0;
    for (MISSION m : keyList) {
      percentage += missionList.get(m).getPercentage();
    }
    return percentage / keyList.size();
  }
}
