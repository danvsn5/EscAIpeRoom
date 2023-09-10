package nz.ac.auckland.se206.gpt;

/** Represents a chat message in the conversation. */
public class ChatMessage {

  private String role;
  private String content;

  /**
   * Constructs a new ChatMessage object with the specified role and content.
   *
   * @param role the role of the message (e.g., "user", "assistant")
   * @param content the content of the message
   */
  public ChatMessage(String role, String content) {
    this.role = role;
    this.content = content;
  }

  /**
   * Returns the role of the chat message.
   *
   * @return the role
   */
  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public void setContent(String content) {
    this.content = content;
  }

  /**
   * Returns the content of the chat message.
   *
   * @return the content
   */
  public String getContent() {
    return content;
  }
}
