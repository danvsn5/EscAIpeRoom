package nz.ac.auckland.se206.missions;

import nz.ac.auckland.se206.controllers.ChatController;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionRequest;

public class WindowMission extends Mission {

  ChatController chatController;

  public WindowMission() {
    currentStage = 0;
    totalStage = 3;
  }

  @Override
  public void initialize() {
    // TODO Set the correct image to visible
  }

  @Override
  public String getName() {
    return "Fix the window";
  }

  @Override
  public ChatMessage askGpt() {
    // TODO ask gpt to generate riddle

    try {
      chatController.invokeRunGpt(
          new ChatMessage("user", GptPromptEngineering.getRiddleWithGivenWord("white")));
    } catch (ApiProxyException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      System.out.println("Error in askGpt");
    }

    return null;
  }

}
