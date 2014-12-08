package com.monkeymusicchallenge.warmup;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

// Hi! Welcome to the Monkey Music Challenge Java starter kit!

public class Main {

  // You control your monkey by sending POST requests to the Monkey Music server
  private static final String SERVER_URL = "http://competition.monkeymusicchallenge.com/";

  public static void main(final String[] args) {

    // Don't forget to provide the right command line arguments
    if (args.length < 2) {
      System.out.println("Usage: java -jar target/warmup.jar <your-team-name> <your-api-key\n");
      if (args.length < 1) {
        System.out.println(" Missing argument: <your-team-name>");
      }
      if (args.length < 2) {
        System.out.println(" Missing argument: <your-api-key>");
      }
      System.exit(1);
    }

    // You identify yourselves by your team name and your API key
    final String teamName = args[0];
    final String apiKey = args[1];

    // You POST to a team-specific URL:
    //   warmup.monkeymusicchallenge.com/team/<your-team-name>
    //
    // Surf to this URL and watch your monkey carry out your commands!
    final String teamUrl = SERVER_URL + "/team/" + teamName;

    // We've put the AI-code in a separate class
    final AI ai = new AI();

    // Allright, time to get started!

    // When we POST a command to the server, it always replies with the current game state
    JSONObject currentGameState = postToServer(teamUrl,
        "command", "new game",
        "apiKey", apiKey);

    // The current game state tells you if you have any turns left to move
    while (currentGameState.getInt("turns") > 0) {
      //System.out.println("Remaining turns: " + currentGameState.getInt("turns"));

      // Use your AI to decide in which direction to move
      final String nextMoveDirection = ai.move(currentGameState);

      // ...and send a new move command to the server
      currentGameState = postToServer(teamUrl,
          "command", "move",
          "direction", nextMoveDirection,
          "apiKey", apiKey);

      // After sending your next move, you'll get the new game state back
    }

    // If the game is over, our server will tell you how you did
    System.out.println("\nGame over!\n");
    // Go to http://warmup.monkeymusicchallenge.com/team/<your-team-name> for more details
    System.out.println("  " + currentGameState.getString("message"));
    System.exit(0);
  }

  // Every time we POST a command to the server, we get the current game state back
  private static JSONObject postToServer(final String teamUrl, final String... args) {
    final Map<String, Object> messageToPost = new HashMap<String, Object>();
    for (int i = 0; i < args.length; i += 2) {
      final String key = args[i];
      final String value = args[i + 1];
      messageToPost.put(key, value);
    }

    try {
      // In this starter kit, we use the Unirest library to send POST requests
      final HttpResponse<JsonNode> response = Unirest.post(teamUrl)
          .header("Content-Type", "application/json")
          .body(new JSONObject(messageToPost).toString())
          .asJson();

      if (response.getCode() == 200) {
        return response.getBody().getObject();
      } else {
        throw new RuntimeException(
            "Server replied with status code " + response.getCode()
            + ", " + response.getBody().getObject().getString("message"));
      }
    } catch (final Exception e) {
      // Hopefully, our server will always be able to handle your requests, but you never know...
      e.printStackTrace();
      System.out.println("Error! We seem to have  trouble talking to the server...\n");
      System.out.println("  " + e.getMessage());
      System.exit(1);
      throw new AssertionError(); // unreachable
    }
  }
}
