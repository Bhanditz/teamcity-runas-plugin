package jetbrains.buildServer.runAs.agent;

import jetbrains.buildServer.dotNet.buildRunner.agent.ResourceGenerator;
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class ShGeneratorTest {
  private static final String LINE_SEPARATOR = System.getProperty("line.separator");

  @DataProvider(name = "cmdLinesCases")
  public Object[][] getCmdLinesCases() {
    return new Object[][] {
      { "cmd line", "cmd line" },
    };
  }

  @Test(dataProvider = "cmdLinesCases")
  public void shouldGenerateContent(@NotNull final String cmdLine, @NotNull final String cmdLineInMessage) {
    // Given
    final ResourceGenerator<Params> instance = createInstance();

    // When
    final String content = instance.create(new Params(cmdLine));

    // Then
    then(content).isEqualTo(cmdLine);
  }

  @NotNull
  private ResourceGenerator<Params> createInstance()
  {
    return new ShGenerator();
  }
}