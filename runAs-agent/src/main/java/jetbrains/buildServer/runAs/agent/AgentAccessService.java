package jetbrains.buildServer.runAs.agent;

import jetbrains.buildServer.agent.AgentLifeCycleAdapter;
import jetbrains.buildServer.agent.AgentLifeCycleListener;
import jetbrains.buildServer.agent.BuildAgent;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.util.positioning.PositionAware;
import jetbrains.buildServer.util.positioning.PositionConstraint;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AgentAccessService extends AgentLifeCycleAdapter implements PositionAware {
  private final AccessControlListProvider myAccessControlListProvider;
  private final FileAccessService myWindowsFileAccessService;
  private final FileAccessService myLinuxFileAccessService;

  public AgentAccessService(
    @NotNull final EventDispatcher<AgentLifeCycleListener> agentDispatcher,
    @NotNull final AccessControlListProvider accessControlListProvider,
    @NotNull final FileAccessService windowsFileAccessService,
    @NotNull final FileAccessService linuxFileAccessService) {
    myAccessControlListProvider = accessControlListProvider;
    myWindowsFileAccessService = windowsFileAccessService;
    myLinuxFileAccessService = linuxFileAccessService;
    agentDispatcher.addListener(this);
  }

  @NotNull
  @Override
  public String getOrderId() {
    return "";
  }

  @NotNull
  @Override
  public PositionConstraint getConstraint() {
    return PositionConstraint.last();
  }

  @Override
  public void agentInitialized(@NotNull final BuildAgent agent) {
    super.agentInitialized(agent);
    final FileAccessService currentFileAccessService = agent.getConfiguration().getSystemInfo().isWindows() ? myWindowsFileAccessService : myLinuxFileAccessService;
    currentFileAccessService.setAccess(myAccessControlListProvider.getAfterAgentInitializedAcl());
  }
}